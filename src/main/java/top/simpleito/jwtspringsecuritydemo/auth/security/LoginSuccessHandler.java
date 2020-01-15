package top.simpleito.jwtspringsecuritydemo.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import top.simpleito.jwtspringsecuritydemo.InMemoryUserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private BusinessJwtTokenGenerator jwtTokenGenerator = new BusinessJwtTokenGenerator();

//    private LoginService loginService = new LoginService();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        response.setHeader("Authorization", "Bearer " + jwtTokenGenerator.generate(request, response, authentication));
//        loginService.loginSuccess((String) authentication.getPrincipal());
        InMemoryUserDao.loginSuccess((String) authentication.getPrincipal());
    }
}
