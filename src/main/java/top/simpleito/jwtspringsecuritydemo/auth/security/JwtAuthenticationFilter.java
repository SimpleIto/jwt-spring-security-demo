package top.simpleito.jwtspringsecuritydemo.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 不要用AbstractAuthenticationProcessingFilter，这个类专门用来处理类似于“登录”那样的认证，即登陆成功后要么跳转，要么直接结束过滤链返回，要么是有状态设置到Session后再从Session取出原受限请求
// 至于为什么，因为原末尾的successfulAuthentication()默认会直接跳过剩余过滤链，然后调用successfullyHandler尝试直接处理返回了。
// 如果设置认证成功继续过滤链 continueChainBeforeSuccessfulAuthentication=true，但上下文设置却又在successfulAuthentication()，最后仍会提示没权限访问。
// 所以此处只能选择覆盖successfulAuthentication
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    protected JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(new RequestHeaderRequestMatcher("Authorization"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        String jwtToken = getJwtToken(request);
        if (StringUtils.isEmpty(jwtToken)){
            return null;
        }

        JwtAuthenticationToken token = new JwtAuthenticationToken(jwtToken, null);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request,response);
    }

    protected String getJwtToken(HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        return authInfo.substring("Bearer ".length(), authInfo.length());
    }
}
