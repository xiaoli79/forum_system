package com.forum_system.common;


//统一状态码！！
public enum ResultCode {
    SUCCESS (0, "成功"),
    FAILED (1000, "操作失败"),
    FAILED_UNAUTHORIZED (1001, "未授权"),
    FAILED_PARAMS_VALIDATE (1002, "参数校验失败"),
    FAILED_FORBIDDEN (1003, "禁⽌访问"),
    FAILED_CREATE (1004, "新增失败"),
    FAILED_NOT_EXISTS (1005, "资源不存在"),



//110#都是关于用户的错误码
    FAILED_USER_EXISTS (1101, "⽤⼾已存在"),
    FAILED_USER_NOT_EXISTS (1102, "⽤⼾不存在"),
    FAILED_LOGIN (1103, "⽤⼾名或密码错误"),
    FAILED_USER_BANNED (1104, "您已被禁⾔, 请联系管理员, 并重新登录."),
    FAILED_USER_ARTICLE_COUNT(1105,"更新帖子数量失败"),

//    120#都是关于板块的错误码
    FAILED_BOARD_ARTICLE_COUNT(1201,"更新帖子数量失败"),


    FAILED_TWO_PWD_NOT_SAME (1105, "两次输⼊的密码不⼀致"),
    ERROR_SERVICES (2000,"服务器内部错误"),
    ERROR_IS_NULL(2001,"IS NULL");


    private int code;
    private String message;

     ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "code=" + code + ", message=" + message;
    }
}
