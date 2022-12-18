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
        } catch (Exception ignored) {}
        return userDetail;
    }
}
