package com.ggm.webserver.Controller;

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
@RequestMapping("/user")
public class UserController {

    @ResponseBody
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("signup : "+authentication.getPrincipal());
        System.out.println(authentication.toString());

        //TODO 로그인 성공! 필요한 정보를 클라이언트에 전달한다.

        return "welcome";
    }

    @ResponseBody
    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
