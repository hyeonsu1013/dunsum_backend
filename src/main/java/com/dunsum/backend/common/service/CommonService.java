package com.dunsum.backend.common.service;

import com.dunsum.backend.common.security.jwt.JwtProvider;
import com.dunsum.backend.common.security.model.AuthUserDetail;
import com.dunsum.backend.common.utils.DunsumStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final JwtProvider jwtProvider;

    public AuthUserDetail getAuthUserDetail(HttpServletRequest request) throws Exception {
        AuthUserDetail userDetail = null;
        try {
            String header = jwtProvider.resolveToken(request);
            if (DunsumStringUtils.isNotBlank(header)) {
                Authentication authentication = jwtProvider.getAuthentication(header);
                userDetail = (AuthUserDetail) authentication.getPrincipal();
            }
        } catch (Exception e) {}
        return userDetail;
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        ip = ip.split(",")[0];
        return ip;
    }
}
