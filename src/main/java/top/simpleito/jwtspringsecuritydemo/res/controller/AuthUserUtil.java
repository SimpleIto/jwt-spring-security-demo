package top.simpleito.jwtspringsecuritydemo.res.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import top.simpleito.jwtspringsecuritydemo.InMemoryUserDao;
import top.simpleito.jwtspringsecuritydemo.auth.JwtAuthenticationToken;

public class AuthUserUtil {

    /**
     * 仅限认证用户使用，否则将抛类型转换异常
     * @return
     */
    public static InMemoryUserDao.User currentAuthenticatedUser(){
        return InMemoryUserDao.getUser(
                ((JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication()).getPrincipal()
        );
    }
}
