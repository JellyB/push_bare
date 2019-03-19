package com.huatu.tiku.push.manager;

import com.alibaba.fastjson.JSONObject;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.annotation.SplitParam;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import com.huatu.tiku.push.dao.NoticeEntityMapper;
import com.huatu.tiku.push.dao.NoticeUserMapper;
import com.huatu.tiku.push.dao.NoticeViewMapper;
import com.huatu.tiku.push.entity.NoticeEntity;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import com.huatu.tiku.push.entity.NoticeView;
import com.huatu.tiku.push.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午11:21
 **/
@Component
@Slf4j
public class NoticeViewManager {

    @Autowired
    private NoticeViewMapper noticeViewMapper;

    @Autowired
    private NoticeEntityMapper noticeEntityMapper;

    @Autowired
    private NoticeUserMapper noticeUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 保存或更新用户 view 数据
     * todo 用队列处理异步
     * @param userId
     * @param noticeId
     * @return
     * @throws BizException
     */
    @Async(value = "threadPoolTaskExecutor")
    public synchronized void saveOrUpdate(long userId, long noticeId)throws BizException{
        NoticeEntity noticeEntity;
        try{
            String key = NoticePushRedisKey.getNoticeEntityKey(noticeId);
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            if(redisTemplate.hasKey(key)){
                String str = String.valueOf(valueOperations.get(key));
                noticeEntity = JSONObject.parseObject(str, NoticeEntity.class);
            }else{
                noticeEntity = noticeEntityMapper.selectByPrimaryKey(noticeId);
            }
            NoticeTypeEnum noticeTypeEnum = NoticeTypeEnum.create(noticeEntity.getType(), noticeEntity.getDetailType());
            NoticeViewEnum noticeViewEnum = noticeTypeEnum.getType().getParent();
            Optional<NoticeView> optionalNoticeView = obtainNoticeView(userId, noticeViewEnum.getView());
            if(optionalNoticeView.isPresent()){
                updateViewLastNoticeAndCount(optionalNoticeView.get(), noticeId);
            }else{
                insertNewView(userId, noticeViewEnum.getView(), noticeId);
            }
        }catch (Exception e){
            log.error("save or update notice view error!:{}", e);
            throw new BizException(NoticePushErrors.SAVE_OR_UPDATE_VIEW_ERROR);
        }

    }

    /**
     * 用户点击单条消息已读之后更新view数量
     * @param userId
     * @param noticeRelationId
     * @throws BizException
     */
    public synchronized void resetViewUnReadCount(long userId, long noticeRelationId) throws BizException{
        NoticeUserRelation noticeUserRelation = (NoticeUserRelation)noticeUserMapper.selectByPrimaryKey(noticeRelationId);
        if(null == noticeUserRelation){
            return;
        }
        NoticeTypeEnum noticeTypeEnum = NoticeTypeEnum.create(noticeUserRelation.getType(), noticeUserRelation.getDetailType());
        NoticeViewEnum noticeViewEnum = noticeTypeEnum.getType().getParent();
        Optional<NoticeView> optionalNoticeView = obtainNoticeView(userId, noticeViewEnum.getView());
        if(optionalNoticeView.isPresent()){
            NoticeView noticeView = optionalNoticeView.get();
            int count = noticeView.getCount() - 1 > 0 ?  noticeView.getCount() - 1 : 0;
            NoticeView update = new NoticeView();
            update.setId(noticeView.getId());
            update.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            update.setCount(count);
            noticeViewMapper.updateByPrimaryKeySelective(update);
        }
    }


    /**
     * 用户点击对应的消息，未读数直接清空
     * @param noticeViewEnum
     * @param userId
     */
    @SplitParam
    public void needReadAll(long userId, NoticeViewEnum noticeViewEnum){
        if(noticeViewEnum.isReadAll()){
        List<String> types = noticeViewEnum.child().stream().map(NoticeParentTypeEnum::getType).collect(Collectors.toList());
        Example example = new Example(NoticeUserRelation.class);
        example.and().andIn("type", types).andEqualTo("userId", userId);

        NoticeUserRelation noticeUserRelation = new NoticeUserRelation();
        noticeUserRelation.setIsRead(NoticeReadEnum.READ.getValue());
        noticeUserRelation.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        noticeUserMapper.updateByExampleSelective(noticeUserRelation, example);
        restViewCount2Zero(userId, noticeViewEnum.getView());
        }
        return;
    }


    /**
     * 获取用户的 notice view
     * @param userId
     * @param view
     * @return
     */
    private Optional<NoticeView> obtainNoticeView(long userId, String view){
        Example example = new Example(NoticeView.class);
        example.and()
                .andEqualTo("userId", userId)
                .andEqualTo("view", view);
        NoticeView noticeView = noticeViewMapper.selectOneByExample(example);
        return Optional.ofNullable(noticeView);
    }

    /**
     * 新增一条视图消息
     * @param userId
     * @param view
     * @return
     */
    private int insertNewView(long userId, String view, long noticeId){
        NoticeView noticeView = new NoticeView();
        noticeView.setUserId(userId);
        noticeView.setNoticeId(noticeId);
        noticeView.setCount(1);
        noticeView.setView(view);
        noticeView.setCreateTime(new Timestamp(System.currentTimeMillis()));
        noticeView.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        noticeView.setStatus(NoticeStatusEnum.NORMAL.getValue());
        return noticeViewMapper.insertSelective(noticeView);
    }

    /**
     * 更新我的视图消息，更新最新的消息id展示在view表中
     * @param noticeView
     * @return
     */
    private int updateViewLastNoticeAndCount(NoticeView noticeView, long noticeId){
        NoticeView noticeView_ = new NoticeView();
        noticeView_.setNoticeId(noticeId);
        noticeView_.setId(noticeView.getId());
        noticeView_.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        noticeView_.setStatus(NoticeStatusEnum.NORMAL.getValue());
        noticeView_.setCount(noticeView.getCount() + 1);
        return noticeViewMapper.updateByPrimaryKeySelective(noticeView_);
    }

    /**
     * 更新用户viewcount
     * @param userId
     * @param view 单个view
     * @return
     */
    public void restViewCount2Zero(long userId, String view){
        NoticeView noticeView = new NoticeView();
        noticeView.setUserId(userId);
        noticeView.setCount(0);
        noticeView.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        Example example = new Example(NoticeView.class);
        Example.Criteria criteria = example.and();
        criteria.andEqualTo("userId", userId);
        if(StringUtils.isNotEmpty(view)){
            criteria.andEqualTo("view", view);
        }
        noticeViewMapper.updateByExampleSelective(noticeView, example);
    }


    /**
     * 查询用户的 view 列表
     * @param userId
     * @return
     */
    public List<NoticeView> noticeViewList(long userId){
        Example example = new Example(NoticeView.class);
        example.and()
                .andEqualTo("userId", userId)
                .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue());
        example.orderBy("updateTime").desc();

        return noticeViewMapper.selectByExample(example);
    }

    /**
     * 根据 example 选择更新 noticeView
      * @param noticeView
     * @param example
     * @return
     */
    public int updateByExampleSelective(NoticeView noticeView, Example example){
        noticeView.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return noticeViewMapper.updateByExampleSelective(noticeView, example);
    }
}
