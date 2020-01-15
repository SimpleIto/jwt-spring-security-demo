package top.simpleito.jwtspringsecuritydemo.auth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthenticationConfigurer<T extends JwtAuthenticationConfigurer<T,B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B>  {
    private JwtAuthenticationFilter filter;

    public JwtAuthenticationConfigurer(RequestMatcher matcher){
        filter = new JwtAuthenticationFilter(matcher);
    }

    @Override
    public void configure(B http) throws Exception {
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationFailureHandler(new HttpStatusAuthenticationFailureHandler());
        http.addFilterBefore(filter, LogoutFilter.class);
    }
}
