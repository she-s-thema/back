package com.ggm.webserver.global.oauth2.handler;

import com.ggm.webserver.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("5:OAuth2LoginSuccessHandler.onAuthenticationSuccess");
        System.out.println("로그인 성공!!!! :"+authentication.getPrincipal());
        System.out.println(authentication.toString());

        // DB에 GUEST role만 추가되어 있는 경우, 회원가입 절차를 계속 해야 한다.
        if(authentication.getAuthorities().stream().anyMatch(s -> s.getAuthority().equals(Role.GUEST.getGrantedAuthority()))){
            System.out.println("회원가입으로 이동합니다");
            response.sendRedirect("/signUp/needMoreInfo");
            return;
        }
        else {
            System.out.println("회원가입 한 사용자입니다.");
            response.sendRedirect("/user/welcome");
            return;

        }

        //if(authentication.getAuthorities().stream().forEach(grantedAuthority -> grantedAuthority.getAuthority()))
    }
}