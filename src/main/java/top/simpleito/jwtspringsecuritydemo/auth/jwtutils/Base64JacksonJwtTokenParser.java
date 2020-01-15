package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.Base64;
import java.util.Iterator;
import java.util.Map;

public class Base64JacksonJwtTokenParser implements JwtTokenParser {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void parse(String token, Map<String, Object> header, Map<String, Object> payload) throws JsonProcessingException {
        String[] parts = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String headerToken = new String(decoder.decode(parts[0]));
        String payloadToken = new String(decoder.decode(parts[1]));

        parse(headerToken, header);
        parse(payloadToken, payload);
    }

    protected void parse(String json, Map<String, Object> map) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
        while (iterator.hasNext()){
            Map.Entry<String, JsonNode> field= iterator.next();
            String key = field.getKey();
            JsonNode valueNode = field.getValue();
            Object value;
            if (valueNode.getNodeType().equals(JsonNodeType.NUMBER)){
                value = valueNode.doubleValue();
            } else {
                value = valueNode.textValue();
            }

            map.put(key, value);
        }
    }


}
