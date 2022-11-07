package com.dunsum.backend.common.security.filters;

import com.dunsum.backend.common.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtProvider.resolveToken(request);
        try {

            if(token != null){
                Jws<Claims> claims = jwtProvider.validateExpiredToken(token);
                if(claims != null){
                    Authentication auth = jwtProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    if(claims.getBody() != null && claims.getBody().containsKey("userNo")){
                        request.setAttribute("userNo", claims.getBody().get("userNo"));
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);

        /*
        TODO : Exception Handling
            SignatureException | MalformedJwtException >> SignatureException error
            ExpiredJwtException >> ExpiredJwtException error
            IllegalArgumentException >> IllegalArgument error
            Exception >> global error
         */
    }
}
