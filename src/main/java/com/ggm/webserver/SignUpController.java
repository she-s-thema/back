package com.ggm.webserver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("signUp")
public class SignUpController {

    @ResponseBody
    @GetMapping("/needMoreInfo")
    public String needMoreInfo(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        //TODO /oauth2/kakao 요청에 대한 처리 결과로
        //TODO DB에 GUEST 권한으로 kakao social_id, social_type만 저장되었다
        //TODO 이름 등 추가 정보를 클라이언트에 요청하자

        return "needMoreInfo";
    }

    @ResponseBody
    @GetMapping("/addMoreInfo")
    public String addMoreInfo(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        //TODO 클라이언트가 보낸 추가정보를 DB에 저장하고 role을 GUEST에서 USER로 업데이트
        //TODO 성공 여부를 클라이언트에 전달하고
        //TODO 성공이면 클라이언트는 다시 /oauth2/kakao 요청을 보낸다 => 로그인 프로세스로 진행

        return "addMoreInfo";
    }
}
