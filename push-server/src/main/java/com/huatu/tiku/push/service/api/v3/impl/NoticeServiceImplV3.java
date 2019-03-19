package com.huatu.tiku.push.service.api.v3.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.annotation.SplitParam;
import com.huatu.tiku.push.dao.NoticeUserMapper;
import com.huatu.tiku.push.dto.NoticeViewVo;
import com.huatu.tiku.push.entity.NoticeEntity;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import com.huatu.tiku.push.entity.NoticeView;
import com.huatu.tiku.push.enums.*;
import com.huatu.tiku.push.manager.NoticeEntityManager;
import com.huatu.tiku.push.manager.NoticeViewManager;
import com.huatu.tiku.push.service.api.strategy.NoticeRespAppStrategy;
import com.huatu.tiku.push.service.api.strategy.NoticeRespHandler;
import com.huatu.tiku.push.service.api.v3.NoticeServiceV3;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-26 下午6:48
 **/

@Service
public class NoticeServiceImplV3 implements NoticeServiceV3{

    @Autowired
    private NoticeUserMapper noticeUserMapper;

    @Autowired
    private NoticeViewManager noticeViewManager;

    @Autowired
    private NoticeEntityManager noticeEntityManager;

    @Autowired
    private NoticeRespHandler noticeRespHandler;

    @Autowired
    @Qualifier(value = "noticeRespAppStrategy")
    private NoticeRespAppStrategy noticeRespAppStrategy;

    /**
     * 全部已读
     * @param userId
     * @return
     * @throws BizException
     */
    @SplitParam
    @Override
    public Object readAll(long userId) throws BizException {
        Example example = new Example(NoticeUserRelation.class);
        example.and()
                .andEqualTo("userId", userId)
                .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue())
                .andEqualTo("isRead", NoticeReadEnum.UN_READ.getValue());

        NoticeUserRelation noticeUserRelation = NoticeUserRelation
                .builder()
                .isRead(NoticeReadEnum.READ.getValue())
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .build();
        noticeViewManager.restViewCount2Zero(userId, StringUtils.EMPTY);
        return noticeUserMapper.updateByExampleSelective(noticeUserRelation, example);
    }

    @Override
    public Object viewList(long userId) throws BizException {
        List<NoticeView> list = noticeViewManager.noticeViewList(userId);
        if(CollectionUtils.isEmpty(list)){
            return Lists.newArrayList();
        }
        Set<Long> noticeIds = list.stream().map(item -> item.getNoticeId()).collect(Collectors.toSet());
        Map<Long, NoticeEntity> noticeEntityMap = noticeEntityManager.obtainNoticeMaps(noticeIds);
        List<NoticeViewVo> noticeViewVos = Lists.newArrayList();
        list.forEach(item -> {
            NoticeViewEnum noticeViewEnum = NoticeViewEnum.create(item.getView());
            NoticeEntity noticeEntity = noticeEntityMap.get(item.getNoticeId());
            StringBuilder content = new StringBuilder();
            if(null == noticeEntity){
                content.append(StringUtils.EMPTY);
            }else{
                content.append(noticeEntity.getTitle());
            }
            NoticeViewVo noticeViewVo = NoticeViewVo
                    .builder()
                    .view(item.getView())
                    .name(noticeViewEnum.getName())
                    .count(item.getCount())
                    .content(content.toString())
                    .timeInfo(NoticeTimeParseUtil.noticeViewTime(item.getUpdateTime()))
                    .build();
            noticeViewVos.add(noticeViewVo);
        });
        return noticeViewVos;
    }

    /**
     * 临时影藏当前view
     * @param userId
     * @param view
     * @return
     * @throws BizException
     */
    @Override
    public Object hideView(long userId, String view) throws BizException {
        Example example = new Example(NoticeView.class);
        example.and()
                .andEqualTo("userId", userId)
                .andEqualTo("view", view);
        NoticeView noticeView = new NoticeView();
        noticeView.setStatus(NoticeStatusEnum.DELETE_LOGIC.getValue());
        return noticeViewManager.updateByExampleSelective(noticeView, example);
    }


    /**
     * 我的 view 消息列表
     * @param userId
     * @param view
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    @Override
    public PageInfo typeViewList(long userId, String view, int page, int size) throws BizException {
        NoticeViewEnum noticeViewEnum = NoticeViewEnum.create(view);
        List<String> types = noticeViewEnum.child().stream().map(NoticeParentTypeEnum::getType).collect(Collectors.toList());
        PageInfo pageInfo = ((NoticeServiceImplV3)AopContext.currentProxy()).obtainViewPageInfo(userId, types, page, size);
        if(CollectionUtils.isEmpty(pageInfo.getList())){
            return pageInfo;
        }
        Set<Long> noticeIds = Sets.newHashSet();
        List<NoticeUserRelation> noticeUserRelations = pageInfo.getList();
        noticeUserRelations.forEach(noticeUserRelation -> {
            noticeIds.add(noticeUserRelation.getNoticeId());
        });
        noticeRespHandler.setAbstractNoticeResp(noticeRespAppStrategy);
        Map<Long, NoticeEntity> maps = noticeEntityManager.obtainNoticeMaps(noticeIds);
        return noticeRespHandler.build(pageInfo, maps);
    }


    @SplitParam
    public PageInfo obtainViewPageInfo(long userId, List<String> types, int page, int size){
        Example example = new Example(NoticeUserRelation.class);
        example.and()
                .andEqualTo("userId", userId)
                .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue())
                .andIn("type", types);

        example.orderBy("createTime").desc();

        PageInfo pageInfo = PageHelper
                .startPage(page, size)
                .doSelectPageInfo(()->noticeUserMapper.selectByExample(example));
            return pageInfo;
    }




    /**
     * 条件删除 noticeId
     * @param userId
     * @param noticeId
     * @return
     * @throws BizException
     */
    @SplitParam
    @Override
    public Object deleteNoticeById(long userId, long noticeId) throws BizException {
        NoticeUserRelation noticeUserRelation = NoticeUserRelation
                .builder()
                .id(noticeId)
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .status(NoticeStatusEnum.DELETE_LOGIC.getValue())
                .build();

        return noticeUserMapper.updateByPrimaryKeySelective(noticeUserRelation);
    }

    /**
     * 点击当前 view 是否需要清空未读消息 count
     *
     * @param userId
     * @param view
     * @throws BizException
     */
    @Override
    public void needReadAll(long userId, String view) throws BizException {
        NoticeViewEnum noticeViewEnum = NoticeViewEnum.create(view);
        noticeViewManager.needReadAll(userId, noticeViewEnum);
    }
}
