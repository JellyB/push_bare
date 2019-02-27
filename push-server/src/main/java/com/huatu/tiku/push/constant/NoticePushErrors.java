package com.huatu.tiku.push.constant;

import com.huatu.common.ErrorResult;

/**
 * 描述：消息推送errors
 *
 * @author biguodong
 * Create time 2018-11-08 下午9:29
 **/
public class NoticePushErrors {
    /**
     * notice 信息
     */
    public static final ErrorResult NOTICE_USER_LIST_EMPTY = ErrorResult.create(1000001, "用户信息不能为空！");

    /**
     * notice 已存在
     */
    public static final ErrorResult NOTICE_ENTITY_HAS_EXIST = ErrorResult.create(1000002, "消息已经存在！");

    /**
     * notice save failed
     */
    public static final ErrorResult NOTICE_ENTITY_SAVE_FAILED = ErrorResult.create(1000002, "消息体保存失败！");
    /**
     * NOTICE 不存在
     */
    public static final ErrorResult NOTICE_ENTITY_UN_EXIST = ErrorResult.create(1000003, "获取消息列表服务异常！");

    /**
     * 消息-user关系列表为空
     */
    public static final ErrorResult NOTICE_USER_RELATIONS_EMPTY = ErrorResult.create(1000004, "用户列表为空！");

    /**
     * 消息-user关系保存失败
     */
    public static final ErrorResult NOTICE_USER_RELATION_SAVE_FAILED = ErrorResult.create(1000005, "消息关系保存失败！");

    /**
     * 消息 - user 关系 noticeID 列表为空
     *
     */
    public static final ErrorResult NOTICE_USER_RELATIONS_LIST_EMPTY = ErrorResult.create(1000004, "用户列表为空！");

    /**
     * 消息枚举类型创建失败
     */
    public static final ErrorResult NOTICE_TYPE_CREATE_ERROR = ErrorResult.create(1000005, "消息类型创建失败！");


    /**
     * course 信息
     */
    public static final ErrorResult COURSE_INFO_UN_EXIST = ErrorResult.create(1000102, "直播课不存在！");
    /**
     * 拉取课程用户信息失败
     */
    public static final ErrorResult COURSE_ORDER_INFO_FETCH_ERROR = ErrorResult.create(1000101, "拉取用户信息失败！");

    /**
     * 直播课开始时间不合法
     */
    public static final ErrorResult COURSE_START_TIME_ILLEGAL = ErrorResult.create(1000103, "直播时间必须晚于当前时间一些！");

    /**
     * job 信息
     */

    /**
     * job 开始时间已过
     */
    public static final ErrorResult START_TIME_EXPIRED = ErrorResult.create(1002001, "job开始时间已过时！");
    /**
     * 更新course 信息失败
     */
    public static final ErrorResult UPDATE_COURSE_INFO_FAILED = ErrorResult.create(1002001, "更新课程扫描信息失败！");

    /**
     * 工厂创建job失败
     */
    public static final ErrorResult JOB_CREATE_BY_FACTORY_FAILED = ErrorResult.create(1002001, "工厂创建job失败！");

    /**
     * job 开始时间不合法
     */

    public static final ErrorResult START_TIME_ILLEGAL = ErrorResult.create(1002002, "job开始时间非法！");

    /**
     * job已经存在
     */
    public static final ErrorResult JOB_EXIST = ErrorResult.create(1000001, "JOB已存在！");

    /**
     * 移除job失败
     */
    public static final ErrorResult JOB_DELETE_FAILED = ErrorResult.create(10000002, "删除job失败！");

    /**
     * 分表参数不存在
     */
    public static final ErrorResult TABLE_SPLIT_PARAMS_EMPTY = ErrorResult.create(10000002, "分表参数不存在！");
    /**
     * 分表参数类型错误
     */
    public static final ErrorResult TABLE_SPLIT_PARAMS_TYPE_ERROR = ErrorResult.create(10000003, "分表参数类型错误！");

    /**
     * 切面执行异常
     */
    public static final ErrorResult TABLE_SPLIT_PARAMS_AOP_ERROR = ErrorResult.create(10000003, "切面执行异常！");

    /**
     * 远程调用参数异常
     */
    public static final ErrorResult REMOVE_CALL_PARAM_ERROR = ErrorResult.create(10000004, "远程调用参数异常！！");

    /**
     * 必须设置数据转移分页数据
     */
    public static final ErrorResult MIGRATE_PAGE_INFO_EMPTY = ErrorResult.create(10000004, "必须设置数据转移分页数据！！");

    /**
     * 保存或更新视图列表失败
     */
    public static final ErrorResult SAVE_OR_UPDATE_VIEW_ERROR = ErrorResult.create(10000005, "保存或更新视图列表失败！！");

}
