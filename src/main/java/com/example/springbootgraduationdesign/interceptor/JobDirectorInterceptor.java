package com.example.springbootgraduationdesign.interceptor;

import com.example.springbootgraduationdesign.component.EncryptComponent;
import com.example.springbootgraduationdesign.component.MyToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class JobDirectorInterceptor implements HandlerInterceptor {
    @Autowired
    private EncryptComponent encryptors;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Optional.ofNullable(request.getHeader(MyToken.AUTHORIZATION))
                .map(auth -> encryptors.decryptToken(auth))
                .ifPresentOrElse(token -> {
                    request.setAttribute(MyToken.ID, token.getId());
                    request.setAttribute(MyToken.ROLE, token.getRole());
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "未登录");
                });
        return true;
    }

}
