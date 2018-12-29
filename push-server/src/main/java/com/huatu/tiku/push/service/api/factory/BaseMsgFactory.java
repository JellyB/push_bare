package com.huatu.tiku.push.service.api.factory;

import com.google.common.collect.Maps;
import com.huatu.tiku.push.constant.BaseMsg;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-27 下午9:26
 **/
public class BaseMsgFactory {

    private static final Map<NoticeTypeEnum, PcModel> MAP = Maps.newHashMap();
    static {
        MAP.put(NoticeTypeEnum.COURSE_REMIND, PcModel.builder().title("课前通知").text(" %s 您的直播课<span class=\"blue\">%s</span>开课，记得准时参加哦~").build());
        MAP.put(NoticeTypeEnum.COURSE_READY, PcModel.builder().title("正在直播").text("您的课程<span class=\"blue\">%s</span>正在直播中，进入课堂和老师一起互动~").build());
        MAP.put(NoticeTypeEnum.MOCK_ONLINE, PcModel.builder().title("模考大赛").text("各位学员请注意，网校<span class=\"blue\">%s</span>开始报名啦~是时候展现真正的技术了！模考时间：%s ，欢迎挑战！").build());
        MAP.put(NoticeTypeEnum.MOCK_REMIND, PcModel.builder().title("模考大赛").text("%s").build());
        MAP.put(NoticeTypeEnum.MOCK_REPORT, PcModel.builder().title("模考大赛").text("%s").build());
        MAP.put(NoticeTypeEnum.ORDER_SEND, PcModel.builder().title("订单已发货").text("您的订单<span class=\"blue\">%s</span>已发货，快递小哥哥/小姐姐正向你全速前进，请耐心等候~").build());
        MAP.put(NoticeTypeEnum.ORDER_SIGN, PcModel.builder().title("订单已签收").text("您的订单<span class=\"blue\">%s</span>已签收，感谢您选择华图在线~").build());
        MAP.put(NoticeTypeEnum.CORRECT_FEEDBACK, PcModel.builder().title("纠错反馈").text("<span class=\"blue\">%s</span> 回复内容：%s").build());
        MAP.put(NoticeTypeEnum.SUGGEST_FEEDBACK, PcModel.builder().title("建议反馈").text("您于 %s 提交的意见反馈 %s 收到了回复：%s").build());
    }


    /**
     * 构建 baseMsg title text
     * @param baseMsg
     * @param noticeTypeEnum
     * @param params
     */
    public static void build(BaseMsg baseMsg, NoticeTypeEnum noticeTypeEnum, String ... params){
        PcModel pcModel = MAP.get(noticeTypeEnum);
        baseMsg.setTitle(pcModel.getTitle());
        baseMsg.setText(String.format(pcModel.getText(), params));
    }


    @Data
    public static class PcModel{
        private String title;
        private String text;

        @Builder
        public PcModel(String title, String text) {
            this.title = title;
            this.text = text;
        }
    }
}
