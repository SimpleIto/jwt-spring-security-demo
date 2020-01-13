package top.simpleito.jwtspringsecuritydemo.auth.service;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import top.simpleito.jwtspringsecuritydemo.auth.LoginConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 一些暂写死的默认约定：
 * 默认对字符以 ISO-8859-1 编码规则进行比特数据加密
 */
@EnableWebSecurity
public class DemoWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());

    }

    private LoginConfigurer loginConfigurer(){
        LoginConfigurer loginConfigurer = new LoginConfigurer("/login","POST");
        loginConfigurer.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        return loginConfigurer;
    }
}
