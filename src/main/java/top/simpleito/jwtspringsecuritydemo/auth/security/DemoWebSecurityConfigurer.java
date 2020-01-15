package top.simpleito.jwtspringsecuritydemo.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * 效果：
 * 无状态服务器
 * 对字符以 ISO-8859-1 编码下的二进制数据进行BASE64编码；同样解析token时，也是Base64解码后以ISO编码获取字串
 * 以用户密码（加密后的）再ISO-8859-1编码下的二进制数据，作为签名算法HMAC的密钥
 * 除登录地址外，均进行JWT校验，读取Header中的Authorize字段，解析JWT
 * JWT校验规则：解析JWT，校验是否被修改（读取"sub"并从库中读取相应密码作为密钥，以HMAC256进行签名计算）；校验是否过期("exp")；通过即合法，看着校验少，其实保证不被修改后 不可能被伪造
 * /login POST为登录地址，用户名和密码字段为username、 password。登录成功后Header中返回JWT
 *
 * @author https://github.com/SimpleIto
 */
@EnableWebSecurity
@Component
public class DemoWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and().apply(loginConfigurer())
                .and().apply(new JwtAuthenticationConfigurer<>(new AntPathRequestMatcher("/**")))
                .and().authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .anyRequest().fullyAuthenticated();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new EncryptCredentialDaoAuthProvider();
        daoAuthenticationProvider.setUserDetailsService(new LoginUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setForcePrincipalAsString(true);
        auth.authenticationProvider(daoAuthenticationProvider);
        auth.eraseCredentials(false);

        auth.authenticationProvider(new JwtAuthenticationProvider());
    }

    @Bean
    public <T extends LoginConfigurer<T,B>, B extends HttpSecurityBuilder<B>> LoginConfigurer<T,B> loginConfigurer(){
        LoginConfigurer loginConfigurer = new LoginConfigurer("/login","POST");
        loginConfigurer.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        return loginConfigurer;
    }
}
