package com.jenkins.ext.jvm.utils;


import com.jenkins.ext.jvm.entity.ResultDto;

public class RestUtils {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final String SUCCESS_MSG = "success";
    public static final String ERROR_MSG = "error";

    public static <T> ResultDto<T> ok() {
        ResultDto<?> ok = ok(true);
        return (ResultDto<T>) ok;
    }

    public static <T> ResultDto<T> ok(T data) {
        return result(SUCCESS, SUCCESS_MSG, data);
    }

    public static <T> ResultDto<T> error(int code, String msg) {
        return result(code, msg, null);
    }

    public static <T> ResultDto<T> error(int code, String msg, T data) {
        return result(code, msg, data);
    }

    public static <T> ResultDto<T> error() {
        return error(ERROR, ERROR_MSG);
    }

    private static <T> ResultDto<T> result(int code, String msg, T data) {
        ResultDto result = new ResultDto<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }
}
