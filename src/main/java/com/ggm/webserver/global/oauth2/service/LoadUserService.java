package com.ggm.webserver.global.oauth2.service;

import com.ggm.webserver.domain.SocialType;
import com.ggm.webserver.global.oauth2.authentication.AccessTokenSocialTypeToken;
import com.ggm.webserver.global.oauth2.authentication.OAuth2UserDetails;
import com.ggm.webserver.global.oauth2.service.strategy.GoogleLoadStrategy;
import com.ggm.webserver.global.oauth2.service.strategy.KakaoLoadStrategy;
import com.ggm.webserver.global.oauth2.service.strategy.NaverLoadStrategy;
import com.ggm.webserver.global.oauth2.service.strategy.SocialLoadStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoadUserService {

    private final RestTemplate restTemplate = new RestTemplate();


    public OAuth2UserDetails getOAuth2UserDetails(AccessTokenSocialTypeToken authentication)  {

        System.out.println("3:LoadUserService.getOAuth2UserDetails");

        SocialType socialType = authentication.getSocialType();

        SocialLoadStrategy socialLoadStrategy = getSocialLoadStrategy(socialType);//SocialLoadStrategy 설정

        String socialPk = socialLoadStrategy.getSocialPk(authentication.getAccessToken());//PK 가져오기

        return OAuth2UserDetails.builder() //PK와 SocialType을 통해 회원 생성
                .socialId(socialPk)
                .socialType(socialType)
                .build();
    }

    private SocialLoadStrategy getSocialLoadStrategy(SocialType socialType) {
        //return new KakaoLoadStrategy();

        SocialLoadStrategy socialLoadStrategy;
        switch (socialType) {
            case KAKAO:
                socialLoadStrategy = new KakaoLoadStrategy();
                break;
            case GOOGLE:
                socialLoadStrategy = new GoogleLoadStrategy();
                break;
            case NAVER:
                socialLoadStrategy =new NaverLoadStrategy();
                break;
            default:
                throw new IllegalArgumentException("지원하지 않는 로그인 형식입니다");
        }
        return socialLoadStrategy;

/*
        return switch (socialType) {
            case KAKAO -> new KakaoLoadStrategy();
            case GOOGLE ->  new GoogleLoadStrategy();
            case NAVER ->  new NaverLoadStrategy();
            default -> throw new IllegalArgumentException("지원하지 않는 로그인 형식입니다");
        };
 */
    }


}