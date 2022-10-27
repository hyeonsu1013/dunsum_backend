package com.dunsum.backend.common.aspect;

import com.dunsum.backend.common.common.dto.SystemDTO;
import com.dunsum.backend.common.common.utils.DunsumStringUtils;
import com.dunsum.backend.common.common.vo.BaseVO;
import com.dunsum.backend.common.common.vo.environment.AppOutsideVO;
import com.dunsum.backend.domains.outside.model.OtsdModel;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class ControllerAuthAspect {

    private final AppOutsideVO appOutsideVO;


    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) && "
            +" execution(* com.dunsum.backend..controller.*Controller.*(..))")
    public void preSetAuthArg(JoinPoint joinPoint) throws Throwable {
        System.out.println("preSetAuthArg");
        //TODO :: 토큰 가져와서 권한 셋팅필요
        long SYS_USER_NO = SystemDTO.SYS_USER_NO;

        ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletContainer.getRequest();

        for (Object arg: joinPoint.getArgs()) {
            String uri = request.getRequestURI();

            if (arg instanceof BaseVO) {
                BaseVO baseVo = (BaseVO) arg;
                if(!DunsumStringUtils.isBlank(uri)){
                    if(this.isRestApiUri(uri)){
                        baseVo.setRgstUserNo(SYS_USER_NO);
                        baseVo.setMdftUserNo(SYS_USER_NO);
                    }
                }
            }

            if(arg instanceof OtsdModel){
                OtsdModel otsdModel = (OtsdModel) arg;
                if(!DunsumStringUtils.isBlank(uri)){
                    if(uri.contains("/otsd/")){
                        String backUri = uri.split("/otsd/")[1];
                        String apiName = backUri.substring(0, backUri.indexOf("/"));
                        otsdModel.setAppConnDataVO(this.appOutsideVO.getConnData(apiName));
                    }
                }
            }
        }
    }

    private boolean isRestApiUri(String uri) {
        String[] apiUris = new String[]{"ins", "upd", "upld", "del"};
        for(String apiUri : apiUris){
            if(uri.endsWith("/" + apiUri) || uri.contains("/" + apiUri + "/")){
                return true;
            }
        }
        return false;
    }

}
