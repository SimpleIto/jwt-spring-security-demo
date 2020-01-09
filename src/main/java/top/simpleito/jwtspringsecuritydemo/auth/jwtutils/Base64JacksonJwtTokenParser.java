package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Iterator;
import java.util.Map;

public class Base64JacksonJwtTokenParser implements JwtTokenParser {
    @Override
    public void parse(String token, Map<String, Object> header, Map<String, Object> payload) throws JsonProcessingException {
        String[] parts = token.split(".");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        Base64.Encoder encoder = Base64.getUrlEncoder();

        String headerToken = decoder.decode()
        String payloadToken = parts[1];

        ObjectMapper mapper = new ObjectMapper();

        //解析header
        JsonNode jsonNode = mapper.readTree(token);
        Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
        while (iterator.)


    }

    protected void parse(String json, Map<String, Object> data){

    }
}
