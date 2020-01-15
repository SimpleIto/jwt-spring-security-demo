package top.simpleito.jwtspringsecuritydemo.auth.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.simpleito.jwtspringsecuritydemo.InMemoryUserDao;

import java.util.ArrayList;

public class LoginUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryUserDao.User myUser = InMemoryUserDao.getUser(username);
        if (myUser == null)
            throw new UsernameNotFoundException("User " + username +" not funded");

        return User.builder()
                .username(username)
                .password(myUser.getPassword())
                .authorities(new ArrayList<>()) //没必要赋权限
                .build();
    }
}
