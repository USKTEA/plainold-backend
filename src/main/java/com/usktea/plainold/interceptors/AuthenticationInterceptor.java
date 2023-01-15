package com.usktea.plainold.interceptors;

import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private JwtUtil jwtUtil;

    public AuthenticationInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            request.setAttribute("username", "guest");

            return true;
        }

        String accessToken = authorization.substring("Bearer ".length());

        Username username = new Username(jwtUtil.decode(accessToken));
        request.setAttribute("username", username);

        return true;
    }
}
