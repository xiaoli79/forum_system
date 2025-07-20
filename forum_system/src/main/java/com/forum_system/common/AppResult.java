package com.forum_system.common;


import lombok.Data;

@Data
public class AppResult<T> {

    private int code;
    private String message;

    private T data;


    public AppResult(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public AppResult(int code, String message,T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 统一结果返回
     * 成功！！
     * @return
     * @param <T>
     */

    public static <T> AppResult<T> success() {
        return new AppResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }


    public static <T> AppResult<T> success(T data) {
        return new AppResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),data);
    }

    public static <T> AppResult<T> success(String message,T data) {
        return new AppResult(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 统一结果返回
     * 失败！
     * @return
     */
    public static AppResult failed(){
        return new AppResult(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }

    public static AppResult failed(String message){
        return new AppResult(ResultCode.FAILED.getCode(), message);
    }
    public static AppResult failed(ResultCode resultCode){
        return new AppResult(resultCode.getCode(), resultCode.getMessage());
    }







}
