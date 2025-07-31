package com.forum_system.interceptor;


import com.forum_system.common.AppConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginInterceptor implements HandlerInterceptor {

//    @Value("${logging.bit-forum.login.url}")
//    private String defaultUrl;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        if( session == null|| session.getAttribute(AppConfig.USER_SESSION) == null) {
//            response.sendRedirect(defaultUrl);
//            return false;
//        }
//        return true;
//    }
}
