package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

/**
 * 计算签名（结果未Base64编码）
 */
public interface SignatureAlgorithm {
    String getJwtAlgName();

    /**
     * 这里不用byte[]做参数，本来是想提供一个口子让用户能规避“约定”参进来
     * 什么“约定”？encodedHeaderAndPayload 其实原JSON内容在"ISO编码"下的二进制数据的Base64编码形式。用户可以通过对该str，以Base64解码后，再以ISO还原为原str内容，再以想要的规则获取二进制数据，再编码
     * 其实想想都一样，照这样说，传byte[]也能用注释给用户说这是Json再iso编码下二进制比特数据
     *
     * 所以最好是Jwt Class中再抽象出一个接口让用户自己把JsonStr -> byte[]或指定字符集；
     * 同时在解析token，以及校验token是否被修改时 先Base64对前俩部分解码后，再用接口byte[] -> JsonStr或者字符集来解析
     */
    byte[] generateSignature(String encodedHeaderAndPayload);
}
