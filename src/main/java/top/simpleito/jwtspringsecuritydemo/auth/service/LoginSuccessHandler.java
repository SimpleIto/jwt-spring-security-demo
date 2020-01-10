package top.simpleito.jwtspringsecuritydemo.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private BusinessJwtTokenGenerator jwtTokenGenerator = new BusinessJwtTokenGenerator();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setHeader("Authorization", "Bearer " + jwtTokenGenerator.generate(request, response, authentication));
    }
}
