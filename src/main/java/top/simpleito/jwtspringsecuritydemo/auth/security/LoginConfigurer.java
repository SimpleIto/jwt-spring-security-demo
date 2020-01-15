package top.simpleito.jwtspringsecuritydemo.auth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// AbstractAuthenticationProcessingFilter 就只做分内的是，比如就专注认证
// 而成功失败的处理，虽然写在 AbstractAuthenticationProcessingFilter 内部，但应有Configurer去配置，这算是约定吧。
public class LoginConfigurer<T extends LoginConfigurer<T,B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B>{
    private LoginFilter authFilter;
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    public LoginConfigurer(String loginUrl, String httpMethod) {
        authFilter = new LoginFilter(new AntPathRequestMatcher(loginUrl, httpMethod));
    }

    @Override
    public void configure(B http) throws Exception {
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authFilter.setAuthenticationFailureHandler(new HttpStatusAuthenticationFailureHandler());
        authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        http.addFilterBefore(authFilter, LogoutFilter.class);
    }

    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return authenticationSuccessHandler;
    }

    public LoginConfigurer<T,B> setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }
}
