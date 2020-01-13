package top.simpleito.jwtspringsecuritydemo.auth.service;

import org.springframework.stereotype.Component;
import top.simpleito.jwtspringsecuritydemo.InMemoryUserDao;

import java.util.Optional;

@Component
public class LoginService {

    public void loginSuccess(String username){
        Optional.ofNullable(InMemoryUserDao.getUser(username))
                .ifPresent(
                        u->u.setLoginTimes(u.getLoginTimes()+1)
                );
    }
}
