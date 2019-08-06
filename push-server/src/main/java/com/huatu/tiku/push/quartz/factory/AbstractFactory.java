package com.huatu.tiku.push.quartz.factory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.*;
import com.huatu.tiku.push.enums.JumpTargetEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述： 友盟消息封装工厂类
 *
 * @author biguodong
 * Create time 2018-11-22 下午5:42
 **/


@Slf4j
public abstract class AbstractFactory {

    public static final AndroidCustomCast androidCustomCast = new AndroidCustomCast();
    public static final AndroidCustomCast androidCustomFileCast = new AndroidCustomCast();
    public static final IosCustomCast iosCustomCast = new IosCustomCast();
    public static final IosCustomCast iosCustomFileCast = new IosCustomCast();

    public static final String TYPE = "type";
    public static final String VIEW = "view";

    /**
     * custom 消息封装
     * @param list
     * @return
     * @throws BizException
     */
    public static ImmutableList<UmengNotification> customCastNotifications(Long bizId, List<NoticeReq> list, JumpTargetEnum jumpTargetEnum)throws BizException{
        List<UmengNotification> notifications = Lists.newArrayList();
        if(CollectionUtils.isEmpty(list)){
            return ImmutableList.copyOf(notifications);
        }

        androidCustomCast(list, notifications, jumpTargetEnum);
        log.info("组装推送数据_custom liveId:{}, 安卓:{}", bizId, JSONObject.toJSONString(notifications));
        iosCustomCast(list, notifications, jumpTargetEnum);
        log.info("组装推送数据_custom liveId:{}, ios:{}", bizId, JSONObject.toJSONString(notifications));
        log.info("当前直播推送数据, liveId:{}, 数据大小:{}, 数据内容:{}", bizId, list.size(), JSONObject.toJSONString(notifications));
        return ImmutableList.copyOf(notifications);
    }


    /**
     * 安卓 custom 消息封装
     * @param list
     * @param notifications
     */
    private static synchronized void androidCustomCast(List<NoticeReq> list, List<UmengNotification> notifications, JumpTargetEnum jumpTargetEnum){
        list.forEach(item -> {
            try{

                List<Long> alias = item.getUsers().stream().map(NoticeReq.NoticeUserRelation::getUserId).collect(Collectors.toList());
                JSONObject custom = parseTargetForAndroid(item.getType(), item.getDetailType(), jumpTargetEnum);

                androidCustomCast.setAlias(Joiner.on(",").join(alias), UmengNotification.ALIAS_TYPE);
                androidCustomCast.setTicker( "新的消息通知！");
                androidCustomCast.setTitle(item.getTitle());
                androidCustomCast.setText(item.getText4Push());
                androidCustomCast.goAppAfterOpen();
                androidCustomCast.setDisplayType(AbstractAndroidNotification.DisplayType.CUSTOM);
                custom.put("title", item.getTitle());
                custom.put("content", item.getText4Push());
                for (String s : item.getCustom().keySet()) {
                    custom.put(s, item.getCustom().get(s));
                }
                androidCustomCast.setCustomField(custom);
                if(!notifications.contains(androidCustomCast)){
                    log.info("notifications 成功添加一条推送数据:{}", androidCustomCast.getClass().getSimpleName());
                    notifications.add(androidCustomCast);
                }else {
                    log.error("尝试添加一条已经存在的推送 androidCustomCast 数据:{}", JSONObject.toJSONString(androidCustomCast));
                }
                log.info("notifications contains current object:{}", notifications.contains(androidCustomCast));
            }catch (Exception e){
                log.error("push msg error", e);
            }
        });
    }


    /**
     * ios custom 消息封装
     * @param list
     * @param notifications
     * @throws BizException
     */
    private static synchronized void iosCustomCast(List<NoticeReq> list, List<UmengNotification> notifications, JumpTargetEnum jumpTargetEnum)throws BizException{
        list.forEach(item -> {
            try{
                List<Long> alias = item.getUsers().stream().map(NoticeReq.NoticeUserRelation::getUserId).collect(Collectors.toList());
                String target = parseTargetForIos(item.getType(), item.getDetailType());
                iosCustomCast.setAlias(Joiner.on(",").join(alias), UmengNotification.ALIAS_TYPE);
                iosCustomCast.setBadge( 1);
                iosCustomCast.setSound( "default");
                iosCustomCast.setAlertTitle(item.getTitle());
                if(StringUtils.isNotEmpty(item.getSubTitle())){
                    iosCustomCast.setAlertSubtitle(item.getSubTitle());
                }
                iosCustomCast.setAlertBody(item.getText4Push());
                for (String s : item.getCustom().keySet()) {
                    iosCustomCast.setCustomizedField(s, String.valueOf(item.getCustom().get(s)));
                }
                iosCustomCast.setCustomizedField(TYPE, jumpTargetEnum.getIosValue());
                iosCustomCast.setCustomizedField(VIEW, target);
                if(!notifications.contains(iosCustomCast)){
                    log.info("notifications 成功添加一条推送数据:{}", iosCustomCast.getClass().getSimpleName());
                    notifications.add(iosCustomCast);
                }else{
                    log.error("尝试添加一条已经存在的推送 iosCustomCast 数据:{}", JSONObject.toJSONString(iosCustomCast));
                }
                log.info("notifications contains current object:{}", notifications.contains(iosCustomCast));
            }catch (Exception e){
                log.error("ios custom push error", e);
            }
        });
    }




    /**
     * custom file 消息封装
     * @param noticeReq
     * @param fileUploadTerminal
     * @return
     * @throws BizException
     */
    public static List<UmengNotification> customFileCastNotifications(NoticeReq noticeReq, FileUploadTerminal fileUploadTerminal, JumpTargetEnum jumpTargetEnum)throws BizException{
        List<UmengNotification> notifications = Lists.newArrayList();
        if(null == fileUploadTerminal){
            return notifications;
        }
        androidCustomFileCast(noticeReq, fileUploadTerminal.getAndroidFileId(), notifications, jumpTargetEnum);
        iosCustomFileCast(noticeReq, fileUploadTerminal.getIosFileId(), notifications, jumpTargetEnum);
        return notifications;
    }

    /**
     * 安卓 custom file 消息封装
     * @param noticeReq
     * @param fileId
     * @param notifications
     */
    private static void androidCustomFileCast(NoticeReq noticeReq, String fileId, List<UmengNotification> notifications, JumpTargetEnum jumpTargetEnum){
        try{
            JSONObject custom = parseTargetForAndroid(noticeReq.getType(), noticeReq.getDetailType(), jumpTargetEnum);
            androidCustomFileCast.setFileId(fileId, UmengNotification.ALIAS_TYPE);
            androidCustomFileCast.setTicker( "新的消息通知！");
            androidCustomFileCast.setTitle(noticeReq.getTitle());
            androidCustomFileCast.setText(noticeReq.getText4Push());
            androidCustomFileCast.goAppAfterOpen();
            androidCustomFileCast.setDisplayType(AbstractAndroidNotification.DisplayType.CUSTOM);
            custom.put("title", noticeReq.getTitle());
            custom.put("content", noticeReq.getText4Data());
            androidCustomFileCast.setCustomField(custom);
            notifications.add(androidCustomFileCast);
        }catch (Exception e){
            log.error("push msg error", e);
        }
    }




    /**
     * ios custom file 消息封装
     * @param noticeReq
     * @param fileId
     * @param notifications
     * @throws BizException
     */
    private static void iosCustomFileCast(NoticeReq noticeReq, String fileId, List<UmengNotification> notifications, JumpTargetEnum jumpTargetEnum)throws BizException{
        try{
            String target = parseTargetForIos(noticeReq.getType(), noticeReq.getDetailType());
            iosCustomFileCast.setFileId(fileId, UmengNotification.ALIAS_TYPE);
            iosCustomFileCast.setBadge( 1);
            iosCustomFileCast.setSound( "default");
            iosCustomFileCast.setAlertTitle(noticeReq.getTitle());
            if(StringUtils.isNotEmpty(noticeReq.getSubTitle())){
                iosCustomFileCast.setAlertSubtitle(noticeReq.getSubTitle());
            }
            iosCustomFileCast.setAlertBody(noticeReq.getText4Push());
            iosCustomFileCast.setCustomizedField(TYPE, jumpTargetEnum.getIosValue());
            iosCustomFileCast.setCustomizedField(VIEW, target);
            notifications.add(iosCustomFileCast);
        }catch (Exception e){
            log.error("ios custom push error", e);
        }
    }




    /**
     * 安卓跳转解析
     * @param type
     * @param detailType
     * @return
     */
    public static JSONObject parseTargetForAndroid(String type, String detailType, JumpTargetEnum jumpTargetEnum){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TYPE, jumpTargetEnum.getAndroidValue());
        if(jumpTargetEnum == JumpTargetEnum.NOTICE_CENTER){
            jsonObject.put(VIEW, parseView(type, detailType));
        }
        return jsonObject;
    }


    /**
     * ios消息跳转解析
     * @param type
     * @return
     */
    public static String parseTargetForIos(String type, String detailType){
        return parseView(type, detailType);
    }

    private static String parseView(String type, String detailType){
        try{
            NoticeTypeEnum noticeTypeEnum = NoticeTypeEnum.create(type, detailType);
            String view = noticeTypeEnum.getType().getParent().getView();
            return view;
        }catch (Exception e){
            log.error("parse view error! type:{}, detailType:{}", type, detailType);
        }
        return StringUtils.EMPTY;
    }

}
