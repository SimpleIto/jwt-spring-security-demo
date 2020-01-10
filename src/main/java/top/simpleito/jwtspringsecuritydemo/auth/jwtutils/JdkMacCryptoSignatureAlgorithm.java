package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class JdkMacCryptoSignatureAlgorithm extends AbstractSignatureAlgorithm {
    public static JdkMacCryptoSignatureAlgorithm HMACSHA256 =
            new JdkMacCryptoSignatureAlgorithm("HS256","HmacSHA256","defaultKey".getBytes());

    private byte[] secret;
    private String jdkAlgName;

    private JdkMacCryptoSignatureAlgorithm(String jwtAlgName, String jdkAlgName, byte[] secret){
        super(jwtAlgName);
        this.jdkAlgName = jdkAlgName;
        this.secret = secret;
    }

    private byte[] calculateHMAC(byte[] data)
            throws  NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret, jdkAlgName);
        Mac mac = Mac.getInstance(jdkAlgName);
        mac.init(secretKeySpec);
        return mac.doFinal(data);
    }

    @Override
    public byte[] generateSignature(String encodedHeaderAndPayload) {
        try {
            return calculateHMAC(encodedHeaderAndPayload.getBytes());
        } catch (Exception e){
            return null;
        }
    }

    public byte[] getSecret() {
        return secret;
    }

    public void setSecret(byte[] secret) {
        this.secret = secret;
    }
}
