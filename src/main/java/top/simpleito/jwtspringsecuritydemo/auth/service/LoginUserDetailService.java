package top.simpleito.jwtspringsecuritydemo.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.simpleito.jwtspringsecuritydemo.InMemoryPermissionDao;
import top.simpleito.jwtspringsecuritydemo.InMemoryUserDao;

@Component
public class LoginUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryUserDao.User myUser = InMemoryUserDao.getUser(username);
        if (myUser == null)
            throw new UsernameNotFoundException("User " + username +" not funded");

        return User.builder()
                .username(username)
                .password(myUser.getPassword())
//                .authorities(InMemoryPermissionDao.getAuthorities(username))
                .build();
    }
}
