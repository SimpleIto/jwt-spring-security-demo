package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

import java.util.Map;

public interface JwtTokenParser {
    void parse(String token, Map<String, Object> header, Map<String, Object> payload) throws Exception;
}
