package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

import java.util.HashMap;
import java.util.Map;

public class JwtToken {
    private JwtTokenParser tokenParser = new Base64JacksonJwtTokenParser();
    private String token;

    private Map<String, Object> header;
    private Map<String, Object> payload;

    private byte[] headerBytes;
    private byte[] payloadBytes;

    private boolean supportGetField = false;
    private boolean
    private boolean hasBuild = false;

    private void convertMapToJsonStringBytes(){
        String json
    }
    private void buildToken(){
        if (header != null){
            convertMapToJsonStringBytes();
        }

    }

    private void parseToken(){

    }

    public JwtToken(String token) {
        this.token = token;
        hasBuild = true;
    }

    public JwtToken(byte[] headerBytes, byte[] payloadBytes){
        this.headerBytes = headerBytes;
        this.payloadBytes = payloadBytes;
    }
    private JwtToken(Map<String, Object> header, Map<String, Object> payload) {
        this.header = header;
        this.payload = payload;
        supportGetField = true;
    }

    public void setTokenParser(JwtTokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    public String getToken() {
        if (hasBuild == false)
            buildToken();
        return token;
    }

    public static class JwtTokenBuilder{
        private JwtTokenHeaderBuilder headerBuilder;
        private JwtTokenPayloadBuilder payloadBuilder;

        public class JwtTokenHeaderBuilder{
            public Map<String, Object> header;

            public JwtTokenHeaderBuilder(){
                header = new HashMap<>();
                typ("jwt");
            }

            public JwtTokenHeaderBuilder field(String key, Object value){
                header.put(key, value);
                return this;
            }
            public JwtTokenHeaderBuilder typ(String typ) { return field("typ", typ); }
            public JwtTokenHeaderBuilder alg(Algorithm alg){ return field("alg", alg.jwtAlg); }

            public JwtTokenPayloadBuilder payload(){ return JwtTokenBuilder.this.payloadBuilder; }
            private Map<String, Object> build(){ return header; }
        }

        public class JwtTokenPayloadBuilder{
            public Map<String, Object> payload;

            public JwtTokenPayloadBuilder field(String key, Object value){
                payload.put(key, value);
                return this;
            }
            public JwtTokenPayloadBuilder sub(String sub){ return field("sub", sub); }
            public JwtTokenPayloadBuilder iat(long iat){ return field("iat", iat); }
            public JwtTokenPayloadBuilder exp(long exp){ return field("exp", exp); }

            public JwtTokenBuilder end(){ return JwtTokenBuilder.this; }
            private Map<String, Object> build(){ return payload; }
        }


        public static JwtTokenBuilder getInstance(){
            return new JwtTokenBuilder();
        }

        public JwtTokenHeaderBuilder header(){
            if (headerBuilder == null)
                headerBuilder = new JwtTokenHeaderBuilder();
            return headerBuilder;
        }

        public JwtToken build(){
            if (headerBuilder == null && payloadBuilder == null)
                throw new RuntimeException("jwt must contains header and payload");
            return new JwtToken(headerBuilder.build(), payloadBuilder.build());
        }
    }

    public static enum Algorithm{
        HS256("HS256", "HmacSHA256");

        private String jwtAlg;
        private String jdkAlgName;

        Algorithm(String jwtAlg, String jdkAlgName){
            this.jwtAlg = jwtAlg;
            this.jdkAlgName = jdkAlgName;
        }
    }
}
