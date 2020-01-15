package top.simpleito.jwtspringsecuritydemo.res;

import org.springframework.security.core.context.SecurityContextHolder;
import top.simpleito.jwtspringsecuritydemo.InMemoryUserDao;
import top.simpleito.jwtspringsecuritydemo.auth.security.JwtAuthenticationToken;

public class AuthUserUtil {


    public static InMemoryUserDao.User currentUser(){
        return InMemoryUserDao.getUser(
                (String) ((JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication()).getJwt().getPayloadField("sub")
        );
    }

    public static String currentUserName(){
        return (String) ((JwtAuthenticationToken)(SecurityContextHolder.getContext().getAuthentication())).getJwt().getPayloadField("sub");
    }
}
