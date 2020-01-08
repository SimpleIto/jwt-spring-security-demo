package top.simpleito.jwtspringsecuritydemo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryPermissionDao {
    private static Map<String, Collection<GrantedAuthority>> userAuthorities;

    static {
        userAuthorities = new HashMap<>();
        insertUserAuthorities("simpleito","RO_ADMIN");
        insertUserAuthorities("jwtuser","RO_LEVEL1");
    }

    private static void insertUserAuthorities(String userName, String... authorities){
        userAuthorities.put(userName,
                Arrays.stream(authorities)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet())
        );
    }

    public static Collection<GrantedAuthority> getAuthorities(String username){
        return userAuthorities.get(username);
    }
}
