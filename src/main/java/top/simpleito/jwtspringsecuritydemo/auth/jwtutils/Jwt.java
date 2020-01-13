package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Jwt {
    private String token;

    private Map<String, Object> header;
    private Map<String, Object> payload;

//    private String encodedHeader;
//    private String encodedPayload;
//    private String encodedSignature;
    private byte[] headerBytes;
    private byte[] payloadBytes;
//    private byte[] encodedHeader;
//    private byte[] encodedPayload;

    private JwtTokenParser tokenParser = new Base64JacksonJwtTokenParser();
    private SignatureAlgorithm signatureAlgorithm;

    private boolean supportAccessField = false;
    private boolean supportAccessToken = false;

    private String convertMapToJsonStringBytes(Map<String,Object> map){
        String json = "{";

        for (Map.Entry<String,Object> entry: map.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            String v;
            if (value instanceof Number)
                v = value.toString();
            else
                v = "\"" + value.toString() + "\"";

            json += ("\"" + key +"\"" + ":" + v + ",");
        }

        if (json.length() > 1)
            json = json.substring(0, json.length()-1);

        return json;
    }

    private void buildToken(){
        Base64.Encoder encoder = Base64.getUrlEncoder();
        if (header != null){
            headerBytes = convertMapToJsonStringBytes(header).getBytes(); //默认对json字串获取其ISO编码规则下byte[]进行加密
            payloadBytes = convertMapToJsonStringBytes(payload).getBytes();
        }
        String t1 = encoder.encodeToString(headerBytes) + "." + encoder.encodeToString(payloadBytes);
        this.token = t1 + "." + encoder.encodeToString(signatureAlgorithm.generateSignature(t1));

        supportAccessToken = true;
    }

    public void parseToken() throws Exception {
        checkCanAccessToken();

        //解析Token为各字段
        String[] tokenParts = token.split(".");
//        encodedHeader = tokenParts[0];
//        encodedPayload = tokenParts[1];
//        encodedSignature = tokenParts[2];
        header = new HashMap<>();
        payload = new HashMap<>();
        tokenParser.parse(token, header, payload);

        supportAccessField = true;
    }

    public Jwt(String token, SignatureAlgorithm signatureAlgorithm){
        this.token = token;
        this.signatureAlgorithm = signatureAlgorithm;
        supportAccessToken = true;
    }

    public Jwt(byte[] headerBytes, byte[] payloadBytes, SignatureAlgorithm signatureAlgorithm){
        this.headerBytes = headerBytes;
        this.payloadBytes = payloadBytes;
        this.signatureAlgorithm = signatureAlgorithm;
    }
    private Jwt(Map<String, Object> header, Map<String, Object> payload) {
        this.header = header;
        this.payload = payload;
        supportAccessField = true;
    }

    public void setTokenParser(JwtTokenParser tokenParser) {
        checkCanAccessField();
        this.tokenParser = tokenParser;
    }
    public Object getHeaderField(String key){
        checkCanAccessField();
        return header.get(key);
    }
    public Object getPayloadField(String key){
        return payload.get(key);
    }

    public String getToken() {
        if (supportAccessToken == false)
            buildToken();
        return token;
    }

    private void checkCanAccessField(){
        if (!supportAccessField)
            throw new RuntimeException("Can't access field. Jwt must be created by Builder Or invoking parseToken()");
    }
    private void checkCanAccessToken(){
        if (!supportAccessToken)
            throw new RuntimeException("Can't access Token");
    }

    public boolean checkIsModified(){
        checkCanAccessToken();
        if (signatureAlgorithm == null)
            throw new RuntimeException("Must Specify SignatureAlgorithm");
        try {
            String[] tokenParts = token.split(".");
            String sig = Base64.getUrlEncoder().encodeToString(signatureAlgorithm.generateSignature(tokenParts[0] + "." + tokenParts[1]));
            while (sig.lastIndexOf("=") == (sig.length() - 1))
                sig = sig.substring(0, sig.length() - 1);
            if (sig.equals(tokenParts[3]))
                return false;
            else
                return true;
        } catch (Exception e){
            return false;
        }

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
