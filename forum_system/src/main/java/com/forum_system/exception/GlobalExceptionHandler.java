package com.forum_system.exception;


import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    以body的形式返回
    @ResponseBody
//    指定要处理的异常
    @ExceptionHandler(ApplicationException.class)
    public AppResult handleApplicationException( ApplicationException e) {

        e.printStackTrace();

        log.error(e.getMessage());
//        获取异常的信息
        if(e.getErrorReuslt() != null){
//            返回异常中记录的状态
            return e.getErrorReuslt();
        }
//        为空
        System.out.println(e.getErrorReuslt());

//        默认返回异常的信息
        System.out.println(11111111);
        return  AppResult.failed(e.getMessage());
    }

    /**
     * 捕捉普通异常！！
     * @param e
     * @return
     */

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public AppResult handleException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        if(e.getMessage() == null) {
            return AppResult.failed(ResultCode.ERROR_SERVICES);
        }
//        不为空~ 这是一个exception
        System.out.println(e.getMessage());
        System.out.println(22222222);
        return AppResult.failed(e.getMessage());
    }
}
