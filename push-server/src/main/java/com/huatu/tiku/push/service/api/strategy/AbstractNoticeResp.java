package com.huatu.tiku.push.service.api.strategy;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.BaseMsg;
import com.huatu.tiku.push.constant.Params;
import com.huatu.tiku.push.entity.NoticeEntity;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import com.huatu.tiku.push.response.NoticeResp;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-27 上午10:46
 **/
@Slf4j
public abstract class AbstractNoticeResp {


    /**
     * 组装pageinfo
     * @param noticeEntity
     * @param baseMsg
     * @return
     */
    protected abstract void filterBaseMsg(BaseMsg baseMsg, NoticeEntity noticeEntity);



    /**
     * 构建pageInfo
     * @param pageInfo
     * @param maps
     * @return
     */
    public final PageInfo build(PageInfo pageInfo, Map<Long, NoticeEntity> maps){
        List<NoticeResp> list = Lists.newArrayList();
        pageInfo.getList().forEach(relations -> {
            NoticeUserRelation noticeUserRelation = (NoticeUserRelation) relations;
            NoticeEntity noticeEntity = maps.get(noticeUserRelation.getNoticeId());

            if(null == noticeEntity){
                log.error("notice entity is null, noticeID:{}", noticeUserRelation.getNoticeId());
                return;
            }
            BaseMsg baseMsg = BaseMsg.builder().build();
            if(StringUtils.isNoneBlank(noticeEntity.getCustom())){
                JSONObject jsonObject = JSONObject.parseObject(noticeEntity.getCustom());
                if(null == jsonObject.get(Params.CREATE_TIME)){
                    jsonObject.put(Params.CREATE_TIME, noticeEntity.getCreateTime());
                }
                Map custom = jsonObject;
                baseMsg.setCustom(custom);
            }
            filterBaseMsg(baseMsg, noticeEntity);

            String noticeTime = NoticeTimeParseUtil.parseTime(noticeUserRelation.getCreateTime().getTime());
            NoticeResp noticeResp = NoticeResp
                    .builder()
                    .noticeId(noticeUserRelation.getId())
                    .noticeTime(noticeTime)
                    .display_type(1)
                    .isRead(noticeUserRelation.getIsRead())
                    .type(noticeEntity.getType())
                    .detailType(noticeEntity.getDetailType())
                    .userId(noticeUserRelation.getUserId())
                    .payload(baseMsg)
                    .build();

            list.add(noticeResp);
        });
        pageInfo.setList(list);
        return pageInfo;
    }
}
