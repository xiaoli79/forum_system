package com.forum_system.exception;

import com.forum_system.common.AppResult;

public class ApplicationException extends RuntimeException {
//    自定义异常
    protected AppResult errorReuslt;

    public ApplicationException(AppResult errorReuslt) {

        super(errorReuslt.getMessage());
        this.errorReuslt = errorReuslt;

    }

//    自定义异常描述
    public ApplicationException(String message) {
        super(message);
    }

//      指定异常
    public ApplicationException(Throwable cause) {
        super(cause);
    }

//      自定义异常描述，异常信息
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }


    public AppResult getErrorReuslt() {
        return errorReuslt;
    }

















}
