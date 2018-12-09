package com.huatu.tiku.push.manager;

import com.google.common.collect.Sets;
import com.huatu.tiku.push.dao.SimpleUserMapper;
import com.huatu.tiku.push.entity.SimpleUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 下午9:05
 **/
@Slf4j
@Component
public class SimpleUserManager {

    @Autowired
    private SimpleUserMapper simpleUserMapper;

    /**
     * 根据id批量查询
     * @param ids
     * @return
     */
    public List<SimpleUser> selectByUserIds(List<Long> ids, String userType, long bizId){
        Example example = new Example(SimpleUser.class);
        example.and()
                .andIn("userId", ids)
                .andEqualTo("userType", userType)
                .andEqualTo("bizId", bizId);

        return simpleUserMapper.selectByExample(example);
    }

    /**
     * 根据bizId及userType存储用户信息
     * @param bizId
     * @param userType
     * @return
     */
    public Set<Integer> selectByBizId(String userType, long bizId){
        Example example = new Example(SimpleUser.class);
        example.and()
                .andEqualTo("bizId", bizId)
                .andEqualTo("userType", userType);

        List<SimpleUser> simpleUserList = simpleUserMapper.selectByExample(example);
        return simpleUserList.stream().map(simpleUser -> {
            Long userId = simpleUser.getUserId();
            Integer userId_ = Integer.valueOf(userId.intValue());
            return userId_;
        }).collect(Collectors.toSet());
    }



    /**
     * 根据names批量查询
     * @param names
     * @returnnames
     */
    public List<SimpleUser> selectByUserNames(List<String> names){
        Example example = new Example(SimpleUser.class);
        example.and()
                .andIn("userName", names);

        return simpleUserMapper.selectByExample(example);
    }

    /**
     * 根据names批量查询userIds
     * @param names
     * @returnnames
     */
    public Set<Integer> selectUserIdByUserNames(List<String> names){
        Example example = new Example(SimpleUser.class);
        example.and()
                .andIn("userName", names);

        List<SimpleUser> simpleUsers = simpleUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(simpleUsers)){
            return Sets.newHashSet();
        }else{
            return simpleUsers.stream().map(simpleUser -> {
                Long userId = simpleUser.getUserId();
                return Integer.valueOf(userId.toString());
            }).collect(Collectors.toSet());
        }
    }


    /**
     * 检查user name是否存在库中
     * @param name
     * @return
     */
    public boolean checkNameExist(String name, String userType, long bizId){
        Example example = new Example(SimpleUser.class);
        example.and()
                .andEqualTo("userName", name)
                .andEqualTo("userType", userType)
                .andEqualTo("bizId", bizId);
        return CollectionUtils.isNotEmpty(simpleUserMapper.selectByExample(example));
    }

    /**
     * 检查user id是否存在库中
     * @param userId
     * @param userType
     * @param bizId
     * @return
     */
    public boolean checkIdExist(long userId,  String userType, long bizId){
        Example example = new Example(SimpleUser.class);
        example.and()
                .andEqualTo("userId", userId)
                .andEqualTo("userType", userType)
                .andEqualTo("bizId", bizId);
        List<SimpleUser> list = simpleUserMapper.selectByExample(example);
        return list.size() > 0;
    }


    /**
     * 批量新增用户信息
     * @param simpleUsers
     * @return
     */
    public int saveSimpleUsers(List<SimpleUser> simpleUsers){
        AtomicInteger count = new AtomicInteger(0);
        simpleUsers.forEach(simpleUser -> {
            this.saveSimpleUser(simpleUser);
            log.info("simple user into mysql: userId:{}, userName:{}", simpleUser.getUserId(), simpleUser.getUserName());
            count.incrementAndGet();
        });
        return count.get();
    }

    /**
     * 新增用户信息
     * @param simpleUser
     *
     */
    public void saveSimpleUser(SimpleUser simpleUser){
        if(checkIdExist(simpleUser.getUserId(), simpleUser.getUserType(), simpleUser.getBizId())){
            return;
        }else{
            simpleUserMapper.insert(simpleUser);
        }
    }
}
