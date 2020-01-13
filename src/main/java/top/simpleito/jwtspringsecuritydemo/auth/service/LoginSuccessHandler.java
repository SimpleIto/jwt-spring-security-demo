package top.simpleito.jwtspringsecuritydemo.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private BusinessJwtTokenGenerator jwtTokenGenerator = new BusinessJwtTokenGenerator();

    @Autowired
    private LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        response.setHeader("Authorization", "Bearer " + jwtTokenGenerator.generate(request, response, authentication));
        loginService.loginSuccess((String) authentication.getPrincipal());
    }
}
