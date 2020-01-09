package top.simpleito.jwtspringsecuritydemo.auth;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JwtTokenGenerator {
    String generate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication);
}
