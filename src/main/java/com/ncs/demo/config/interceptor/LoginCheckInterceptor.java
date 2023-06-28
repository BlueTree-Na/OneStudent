package com.ncs.demo.config.interceptor;

import com.ncs.demo.config.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 로그인 x 페이지 접근시 URI
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(SessionConst.LOGIN_SESSION_KEY) == null){
            log.info("미 인증 사용자 요청 URI:{}", requestURI);
            response.sendRedirect("/login?redirectURI="+requestURI);
            return false;
        }

        return true;
    }
}
