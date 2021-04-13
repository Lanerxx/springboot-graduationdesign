package com.example.springbootgraduationdesign.component;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class RequestComponent {
    public int getUid() {
        return (int) RequestContextHolder.currentRequestAttributes()
                .getAttribute("id", RequestAttributes.SCOPE_REQUEST);
    }

    public MyToken.ROLES getRole() {
        return (MyToken.ROLES) RequestContextHolder.currentRequestAttributes()
                .getAttribute("role", RequestAttributes.SCOPE_REQUEST);
    }
}
