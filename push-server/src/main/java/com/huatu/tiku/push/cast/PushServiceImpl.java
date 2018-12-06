package com.huatu.tiku.push.cast;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 下午2:57
 **/
@Service
@Slf4j
public class PushServiceImpl<T> implements PushService<T>{

    @Autowired
    private AndroidCustomCast androidCustomCast;

    @Autowired
    private IosCustomCast iosCustomCast;

    @Autowired
    private PushClient pushClient;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 消息推送入口
     *
     * @param o
     * @throws BizException
     */
    @Override
    public void push(Object o) throws BizException {

    }

    /**
     * 消息推送
     *
     *
     * @param list
     * @return
     */
    @Override
    public void push(List<NoticeReq> list) {

        if(CollectionUtils.isEmpty(list)){
            return;
        }


        list.forEach(item -> {
            try{

                List<Long> alias = item.getUsers().stream().map(NoticeReq.NoticeUserRelation::getUserId).collect(Collectors.toList());
                JSONObject custom = parseTargetForAndroid(item.getType(), item.getDetailType());

                androidCustomCast.setAlias(Joiner.on(",").join(alias), UmengNotification.ALIAS_TYPE);
                androidCustomCast.setTicker( "新的消息通知！");
                androidCustomCast.setTitle(item.getTitle());
                androidCustomCast.setText(item.getText());
                androidCustomCast.goAppAfterOpen();
                androidCustomCast.setDisplayType(AbstractAndroidNotification.DisplayType.CUSTOM);
                androidCustomCast.setTestMode();
                custom.put("title", item.getTitle());
                custom.put("content", item.getText());
                androidCustomCast.setCustomField(custom);
                pushClient.send(androidCustomCast);
            }catch (Exception e){
                log.error("push msg error", e);
            }
        });

        list.forEach(item -> {
            try{
                List<Long> alias = item.getUsers().stream().map(NoticeReq.NoticeUserRelation::getUserId).collect(Collectors.toList());
                String custom = parseTargetForIos(item.getType(), item.getDetailType());
                iosCustomCast.setAlias(Joiner.on(",").join(alias), UmengNotification.ALIAS_TYPE);
                iosCustomCast.setBadge( 1);
                iosCustomCast.setSound( "default");
                iosCustomCast.setAlertTitle(item.getTitle());
                if(StringUtils.isNotEmpty(item.getSubTitle())){
                    iosCustomCast.setAlertSubtitle(item.getSubTitle());
                }
                iosCustomCast.setAlertBody(item.getText());
                iosCustomCast.setCustomizedField("type", custom);
                // TODO set 'production_mode' to 'true' if your app is under production mode
                iosCustomCast.setTestMode();
                pushClient.send(iosCustomCast);
            }catch (Exception e){
                log.error("ios custom push error", e);
            }
        });

    }


    /**
     * 终端上传file_id
     *
     * @param classId
     * @return
     * @throws BizException
     */
    @Override
    public FileUploadTerminal uploadTerminal(long classId) throws BizException {

        String key = NoticePushRedisKey.getCourseClassId(classId);
        SetOperations setOperations = redisTemplate.opsForSet();
        Set<Long> userIds = setOperations.members(key);
        String alias = Joiner.on("\n").join(userIds);
        String androidFileId = androidCustomCast.uploadContents(alias);
        String iosFileId = iosCustomCast.uploadContents(alias);
        return FileUploadTerminal
                .builder()
                .androidFileId(androidFileId)
                .iosFileId(iosFileId)
                .build();
    }


    /**
     * 安卓跳转解析
     * @param type
     * @param detailType
     * @return
     */
    public JSONObject parseTargetForAndroid(String type, String detailType){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", 12);
        return jsonObject;
        /*switch (type){
            case CourseParams.TYPE:
                jsonObject.put("type", 12);
                return jsonObject;
            case FeedBackParams.TYPE:
                jsonObject.put("type", 12);
                return jsonObject;
            case MockParams.TYPE:
                jsonObject.put("type", 12);
                return jsonObject;
            default:
                return jsonObject;
        }*/
    }

    /**
     * ios消息跳转解析
     * @param type
     * @return
     */
    public String parseTargetForIos(String type, String detailType){
        return "ht://noticeCenter";
        /*switch (type){
            case CourseParams.TYPE:
                return "ht://course";
            case FeedBackParams.TYPE:
                return "ht://feedback";
            case MockParams.TYPE:
                return "ht://mock";
            default:
                return "";

        }*/
    }
}
