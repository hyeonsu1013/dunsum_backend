//package com.dunsum.backend.common.security.jwt;
//
//import org.json.JSONObject;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtAccessDeniedHandler implements AccessDeniedHandler {
//
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        // body 에러 설명 메시지를 추가하기 위해 JSON Object, writer 사용
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.getWriter().write(new JSONObject()
//                .put("message", accessDeniedException.getMessage()).toString());
//    }
//}
