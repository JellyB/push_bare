package com.huatu.tiku.push.dao.biz;

import com.huatu.tiku.push.entity.NoticeUserRelation;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-06 下午4:22
 **/
@Repository
public interface NoticeUserMapper extends Mapper<NoticeUserRelation> {
}
