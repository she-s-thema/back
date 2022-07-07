//package com.ggm.webserver.global.config;
//
//import com.ggm.webserver.global.oauth2.filter.OAuth2AccessTokenAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final OAuth2AccessTokenAuthenticationFilter oAuth2AccessTokenAuthenticationFilter;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        //== 접근 제한 ==//
//        http.authorizeRequests()
//                .antMatchers("*").permitAll();
////                .antMatchers("/*").permitAll() //메인 화면 접근 가능
////                .antMatchers("/kakaoToken/*").permitAll() //카카오 인증
////                .antMatchers("/oauth2/*").permitAll() //소셜로그인 필터
////                .antMatchers("/signUp/*").hasRole("GUEST") //회원가입 접근 가능
////                .anyRequest().hasRole("USER");
//
//        http.addFilterBefore(oAuth2AccessTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//    }
//
//}