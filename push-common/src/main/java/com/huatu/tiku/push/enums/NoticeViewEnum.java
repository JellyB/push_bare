package com.huatu.tiku.push.enums;

import com.huatu.tiku.push.constant.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午9:49
 **/
@AllArgsConstructor
@Getter
public enum  NoticeViewEnum {
    /**
     * 课程通知
     */
    COURSE("课程通知", NoticeViewConstant.COURSE, false),
    /**
     * 平台通知
     */
    PLAT_FORM("平台通知", NoticeViewConstant.PLAT_FORM, false),
    /**
     * 物流通知
     */
    LOGISTICS("物流通知", NoticeViewConstant.LOGISTICS, true),

    /**
     * 反馈通知
     */
    FEED_BACK("反馈通知", NoticeViewConstant.FEED_BACK, true);

    private String name;

    private String view;

    /**
     * 此类型的数据用户点击后直接清空未读数
     */
    private boolean readAll;

    public static NoticeViewEnum create(String view){
        for(NoticeViewEnum noticeViewEnum : values()){
            if(noticeViewEnum.getView().equals(view)){
                return noticeViewEnum;
            }
        }
        return null;
    }

    /**
     * 根据view 类型获取 parent 孩子列表
     * @return
     */
    public List<NoticeParentTypeEnum> child(){
        List<NoticeParentTypeEnum> list = new ArrayList<>();
        for(NoticeParentTypeEnum noticeParentTypeEnum : NoticeParentTypeEnum.values()){
            if(noticeParentTypeEnum.getParent() == this){
                list.add(noticeParentTypeEnum);
            }
        }
        return list;
    }
}
