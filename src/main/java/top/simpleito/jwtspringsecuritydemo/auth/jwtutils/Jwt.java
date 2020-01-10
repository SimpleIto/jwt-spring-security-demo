package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

import java.util.HashMap;
import java.util.Map;

public class Jwt {
    private String token;

    private Map<String, Object> header;
    private Map<String, Object> payload;

    private String encodedHeader;
    private String encodedPayload;
    private String encodedSignature;
    private byte[] headerBytes;
    private byte[] payloadBytes;
//    private byte[] encodedHeader;
//    private byte[] encodedPayload;

    private JwtTokenParser tokenParser = new Base64JacksonJwtTokenParser();
    private SignatureAlgorithm signatureAlgorithm;

    private boolean supportGetField = false;
    private boolean supportGetToken = false;

    private void convertMapToJsonStringBytes(){
        String json
    }
    private void buildToken(){
        if (header != null){
            convertMapToJsonStringBytes();
        }

    }

    public void parseToken() throws Exception {
        header = new HashMap<>();
        payload = new HashMap<>();
        tokenParser.parse(token, header, payload);
    }

    public Jwt(String token, SignatureAlgorithm signatureAlgorithm) throws Exception {
        this.token = token;
        this.signatureAlgorithm = signatureAlgorithm;
        supportGetToken = true;

        //TODO：不要在创建时就尝试分部分，这算是解析。因此这里没必要抛出异常。考虑添加一个字段 isTokenParsed
        try {
            String[] tokenParts = token.split(".");
            encodedHeader = tokenParts[0];
            encodedPayload = tokenParts[1];
            encodedSignature = tokenParts[2];
        } catch (IndexOutOfBoundsException e){
            throw new Exception("invalid Token");
        }

    }

    public Jwt(byte[] headerBytes, byte[] payloadBytes, SignatureAlgorithm signatureAlgorithm){
        this.headerBytes = headerBytes;
        this.payloadBytes = payloadBytes;
        this.signatureAlgorithm = signatureAlgorithm;
    }
    private Jwt(Map<String, Object> header, Map<String, Object> payload) {
        this.header = header;
        this.payload = payload;
        supportGetField = true;
    }

    public void setTokenParser(JwtTokenParser tokenParser) {
        checkCanGetField();
        this.tokenParser = tokenParser;
    }
    public Object getHeaderField(String key){
        checkCanGetField();
        return header.get(key);
    }
    public Object getPayloadField(String key){
        return payload.get(key);
    }

    public String getToken() {
        if (supportGetToken == false)
            buildToken();
        return token;
    }
    private void checkCanGetField(){
        if (!supportGetField)
            throw new RuntimeException("Can't get field. Jwt must be created by Builder Or invoking parseToken()");
    }
    public boolean checkIsModified(SignatureAlgorithm algorithm){
        String signature =
    }






    public static class JwtBuilder {
        private JwtHeaderBuilder headerBuilder;
        private JwtPayloadBuilder payloadBuilder;
        private SignatureAlgorithm signatureAlgorithm;

        public class JwtHeaderBuilder {
            public Map<String, Object> header;

            public JwtHeaderBuilder(){
                header = new HashMap<>();
                typ("jwt");
            }

            public JwtHeaderBuilder field(String key, Object value){
                header.put(key, value);
                return this;
            }
            public JwtHeaderBuilder typ(String typ) { return field("typ", typ); }
            public JwtHeaderBuilder alg(SignatureAlgorithm alg){
                JwtBuilder.this.signatureAlgorithm = alg;
                return field("alg", alg.getJwtAlgName());
            }

            public JwtPayloadBuilder payload(){ return JwtBuilder.this.payloadBuilder; }
            private Map<String, Object> build(){ return header; }
        }

        public class JwtPayloadBuilder {
            public Map<String, Object> payload;

            public JwtPayloadBuilder field(String key, Object value){
                payload.put(key, value);
                return this;
            }
            public JwtPayloadBuilder sub(String sub){ return field("sub", sub); }
            public JwtPayloadBuilder iat(long iat){ return field("iat", iat); }
            public JwtPayloadBuilder exp(long exp){ return field("exp", exp); }

            public JwtBuilder end(){ return JwtBuilder.this; }
            private Map<String, Object> build(){ return payload; }

        }


        public static JwtBuilder getInstance(){
            return new JwtBuilder();
        }

        public JwtHeaderBuilder header(){
            if (headerBuilder == null)
                headerBuilder = new JwtHeaderBuilder();
            return headerBuilder;
        }

        public Jwt build(){
            if ((headerBuilder == null && payloadBuilder == null)
                || signatureAlgorithm == null)
                throw new RuntimeException("jwt must contains header and payload and alg");
            return new Jwt(headerBuilder.build(), payloadBuilder.build());
        }
    }


}
