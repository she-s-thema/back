package com.ggm.webserver.Controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

// 로그인 요청 URL
//kauth.kakao.com/oauth/authorize?client_id=9240bec26b639066d5ac5afdbaeb6bb0&redirect_uri=http://localhost/auth/kakao/callback&response_type=code
@RestController
@RequiredArgsConstructor
@ComponentScan
@Slf4j

@RequestMapping("/auth/kakao")
public class KakaoTokenController {
    @Value("${kakao.secret}")
    String secret;
    @Value("${kakao.client-id}")
    String client_id;
    @Value("${kakao.redirect-uri}")
    String redirect_uri;
    @Value("${kakao.authorize-uri}")
    String authorize_uri;


    @ResponseBody
    @GetMapping("/login")
    public void kakaoLogin(HttpServletResponse httpServletResponse) throws IOException {
        try {
            httpServletResponse.sendRedirect(authorize_uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//         참고 https://velog.io/@shwncho/Spring-Boot-%EC%B9%B4%EC%B9%B4%EC%98%A4-%EB%A1%9C%EA%B7%B8%EC%9D%B8-APIoAuth-2.0

    @ResponseBody

    @GetMapping("/callback")
    public String kakaoCallback(@RequestParam String code) {

        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + client_id); // REST_API_KEY
            sb.append("&redirect_uri=" + redirect_uri); // 인가코드 받은 redirect_uri
            sb.append("&code=" + code);
            sb.append("&client_secret=" + secret);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "access_Token : " + access_Token;
    }

    @ResponseBody
    @GetMapping("/getkakaoprofile")
    public String getKakaoProfile(@RequestParam(required = false) String token) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String resultString = "";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result = result + line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            resultString = result;

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("id : " + id);
            System.out.println("email : " + email);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultString;
    }
}


