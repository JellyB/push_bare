package com.huatu.tiku.push.constant;

/**
 *  response
 * @author zhaoxi
 **/

import com.huatu.tiku.push.enums.ResponseMsgEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMsg<T> {
    private int code;
    private String msg;
    private T data;

    public static ResponseMsg success(Object data) {
        return ResponseMsg.builder().code(200).msg("").data(data).build();
    }

    public static ResponseMsg success() {
        return ResponseMsg.builder().code(200).msg("").data(null).build();
    }

    public static ResponseMsg error(Object data, int code, String msg) {
        return ResponseMsg.builder().code(code).msg(msg).data(data).build();
    }

    public static ResponseMsg error(Object data, int code) {
        return error(data, code, ResponseMsgEnum.API_SERVER_ERROR.getMsg());
    }

    public static ResponseMsg error(int code) {
        return error(null, code);
    }

    public static ResponseMsg error(int code, String msg) {
        return error(null, code, msg);
    }

}