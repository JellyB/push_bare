package com.huatu.tiku.push.enums;


public enum ResponseMsgEnum {

    SERVER_ERROR("服务器异常，请联系管理员",500),
    SERVER_FORBIDDEN("参数有误或资源不存在，请联系管理员",403),
    API_SERVER_ERROR("系统繁忙，请稍后重试",500);

    private ResponseMsgEnum(String msg, int code){
        this.code= code;
        this.msg = msg;
    }
    private String msg;
    private int code;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
