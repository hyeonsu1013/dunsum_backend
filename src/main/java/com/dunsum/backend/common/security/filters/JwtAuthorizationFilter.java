package com.dunsum.backend.common.security.filters;

import com.dunsum.backend.common.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
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
//    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            String token = bearerToken.substring("Bearer ".length());

            // 1차 체크(정보가 변조되지 않았는지 체크)
//            if (jwtProvider.verify(token)) {
//                Map<String, Object> claims = jwtProvider.getClaims(token);

                // 캐시(레디스) 사용
//                Map<String, Object> memberMap = memberService.getMemberMapByUsername__cached((String) claims.get("username"));
//                Member member = Member.fromMap(memberMap);

                // 내부 호출을 막으려면 사용
                /*
                Member member = memberService.getByUsername__cached((String) claims.get("username"));
                 */

                // 2차 체크(화이트리스트에 포함되는지)
//                if ( memberService.verifyWithWhiteList(member, token) ) {
//                    forceAuthentication(member);
//                }
//            }
        }

        filterChain.doFilter(request, response);
    }

//    private void forceAuthentication(Member member) {
//        MemberContext memberContext = new MemberContext(member);
//
//        UsernamePasswordAuthenticationToken authentication =
//                UsernamePasswordAuthenticationToken.authenticated(
//                        memberContext,
//                        null,
//                        member.getAuthorities()
//                );
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//    }
}
