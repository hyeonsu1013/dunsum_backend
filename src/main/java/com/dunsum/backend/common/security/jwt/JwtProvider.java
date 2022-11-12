package com.dunsum.backend.common.security.jwt;

import com.dunsum.backend.common.security.SecurityConfig;
import com.dunsum.backend.common.security.model.AuthUserDetail;
import com.dunsum.backend.common.security.model.TokenUserModel;
import com.dunsum.backend.common.utils.DunsumStringUtils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private SecretKey jwtSecretKey;

    private String header = SecurityConfig.HEADER_TOKEN;

    // SecretKey 생성
    @PostConstruct
    public void generateSecretKey() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        this.jwtSecretKey = keyGenerator.generateKey();
    }

    // AccessToken 생성
    public String generateAccessToken(TokenUserModel user) {
        if(user == null){
            return null;
        }

        // Registered claim. 토큰에 대한 정보들이 담겨있는 클레임. 이미 이름이 등록되어있다.
        // 토큰제목(sub). 고유 식별자를 넣는다.
        Claims claims = Jwts.claims().setSubject(String.valueOf(user.getLginId()));
        claims.put("userNo", user.getUserNo());
        claims.put("userNknm", user.getUserNknm());
        claims.put("lginId", user.getLginId());
        claims.put("gustYn", user.getGustYn());
        claims.put("auth", user.getAuth());
        claims.put("authNm", user.getAuthNm());

        Date nowDate = new Date();
        Date exDate = this.getNextExprDate();

        return Jwts.builder()
                // 헤더의 타입(typ)을 jwt 설정
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims) // 데이터
                .setIssuedAt(nowDate) // 토큰 발행일
                .setExpiration(exDate) // set Expire Time
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256) // 서명에 사용할 키와 해싱 알고리즘(alt) 설정
                .compact();
    }

    // RefreshToken 생성
    public String generateRefreshToken() {

        Claims claims = Jwts.claims();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(this.getNextExprDate())
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {

        Claims claims = this.getClaims(token).getBody();

        TokenUserModel tokenUser = claimsToObject(claims);
        AuthUserDetail userDetail = new AuthUserDetail();

        userDetail.setUserToken(token);
        userDetail.setUserNo(tokenUser.getUserNo());
        userDetail.setUsername(tokenUser.getUserNknm());
        userDetail.setLginId(tokenUser.getLginId());
        userDetail.setGustYn(tokenUser.getGustYn());

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : userDetail.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        userDetail.setAuthorities(authorities);

        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
    }

    private TokenUserModel claimsToObject(Claims body) {

        TokenUserModel tokenUser = new TokenUserModel();

        String userNo = DunsumStringUtils.isBlank(body.get("userNo").toString()) ? "0" : body.get("userNo").toString();
        tokenUser.setUserNo(Long.parseLong(userNo));
        tokenUser.setLginId(String.valueOf(body.get("userNknm")));
        tokenUser.setLginId(String.valueOf(body.get("lginId")));
        tokenUser.setGustYn(String.valueOf(body.get("gustYn")));
        tokenUser.setAuth(String.valueOf(body.get("auth")));
        tokenUser.setAuthNm(String.valueOf(body.get("authNm")));

        return tokenUser;
    }

    // Request Header 에서 token 파싱
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(header);
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public Jws<Claims> validateExpiredToken(String token) {
        try {
            Jws<Claims> claims = this.getClaims(token);
            if(!claims.getBody().getExpiration().before(new Date())) {
                return claims;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public TokenUserModel getTokenInfo(String token) throws ExpiredJwtException {

        TokenUserModel tokenUser = new TokenUserModel();
        Jws<Claims> claims = this.validateExpiredToken(token);

        if(claims != null) {
            tokenUser = claimsToObject(claims.getBody());
        }
        return tokenUser;
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token);
    }

    // 만료일자 구하기
    private Date getNextExprDate() {
        Date nowDate = new Date();

        // 만료일자 - 다음날 오전 6시
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }
}
