package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

/**
 * 计算签名（结果未Base64编码）
 */
public interface SignatureAlgorithm {
    String getJwtAlgName();

    /**
     * 这里之所以不用byte[]作为参数，因为那样就没有机会规避“约定”参进来
     *
     * 什么“约定”？该参数，实际是在Jwt中对header、payload，Base64.encode()的结果。而encode()是原byte[]数据在ascii编码下的映射结果。万一我签名并不想hash目标字符串在ascii下的byte[]结果呢？
     *
     * 那为什么不直接传 原header byte[]和 原payload byte[]呢？因为在getToken时，也要计算一趟编码后的header和token，按jwt规范计算签名中也会用到，那就重复计算了。
     * 那计算编码后header和payload时的，Base64 encode()不算引入“约定”吗？ 不算，因为该计算byte[]并未被外部使用，而是直接生成目标token字符串。
     */
    byte[] generateSignature(String encodedHeaderAndPayload);
}
