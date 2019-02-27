package com.huatu.tiku.push.manager;

import com.google.common.collect.Maps;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.dao.NoticeEntityMapper;
import com.huatu.tiku.push.entity.NoticeEntity;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 下午3:46
 **/

@Component
@Slf4j
public class NoticeEntityManager {

    @Autowired
    private NoticeEntityMapper noticeEntityMapper;

    /**
     * 根据多个notice id 查询 notice 表信息 返回map
     * @param noticeIds
     * @return
     */
    public Map<Long, NoticeEntity> obtainNoticeMaps(Set<Long> noticeIds){
        if(CollectionUtils.isEmpty(noticeIds)){
            throw new BizException(NoticePushErrors.NOTICE_USER_RELATIONS_LIST_EMPTY);
        }
        Example example = new Example(NoticeEntity.class);
        example.and()
                .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue())
                .andIn("id", noticeIds);
        List<NoticeEntity> list = noticeEntityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(i-> i.getId(), i -> i));
    }

}
