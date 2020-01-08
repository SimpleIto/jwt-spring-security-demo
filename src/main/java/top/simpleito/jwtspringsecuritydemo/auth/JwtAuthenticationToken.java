package top.simpleito.jwtspringsecuritydemo.auth;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Formatter;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;


    private String username;
    private String signature;
    private Integer exp;

    public JwtAuthenticationToken(String token, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.token = token;
    }

//    public void initWithToken(){
//        try {
//            String origin header
//            byte[] decodedTokenBytes = Base64.getUrlDecoder().decode(token.substring(0,token.lastIndexOf(".")).getBytes(StandardCharsets.UTF_8));
//            String decodedToken = Base64.getEncoder().encodeToString(decodedTokenBytes);
//
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode headerJsonNode = mapper.readTree(decodedToken.substring());
//            headerJsonNode.
//            JsonNode payloadJsonNode = mapper.readTree()
//
//            JsonParserFactory.getJsonParser().parseMap()
//        } catch (Exception e){
//            throw new BadCredentialsException("authenticate failed");
//        }
//    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public String getPrincipal() {
        return username;
    }
}
