package com.huatu.tiku.push.dao;

import com.huatu.tiku.push.annotation.TableSplit;
import com.huatu.tiku.push.dao.strategy.Strategy;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import org.apache.ibatis.annotations.InsertProvider;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-06 下午4:22
 **/
@Repository
@TableSplit(value = Strategy.NOTICE_RELATION, strategy = Strategy.MOLD_STRATEGY)
public interface NoticeUserMapper<T> extends Mapper<NoticeUserRelation>{


}
