import com.zzrq.base.utils.RSAUtil;
import com.zzrq.base.utils.RequestUtil;
import com.zzrq.base.dto.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class TestToken {
    private static final String pubKeyPath = "D:\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "D:\\tmp\\rsa\\rsa.pri";

    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3yRhX7DrNbMBOjBAvTwYKfmYjole9O8yC0w57HhGPfm/7moGlt8tD66giJOnNJftoDw20laAxem6CZ38L5L5nAQJHtmnDUNjcs+1XysV0npK1yNeM0CBQ8Z1g/1+PcGylIjRp+eiV0xW1k9PkmWCJboLH+3HO3OdFn++ic5ypfwIDAQAB";

    private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALfJGFfsOs1swE6MEC9PBgp+ZiOiV707zILTDnseEY9+b/uagaW3y0PrqCIk6c0l+2gPDbSVoDF6boJnfwvkvmcBAke2acNQ2Nyz7VfKxXSekrXI14zQIFDxnWD/X49wbKUiNGn56JXTFbWT0+SZYIlugsf7cc7c50Wf76JznKl/AgMBAAECgYAWqVwwEzfY9qzrwoeIXdUf3CAaCr2I3uPDyeSCN93wdKW4P3/3ACe3M0qjEywlGKbcWObWupBrKW6Q89QNkmILuW++erjD/uSX4ssbaBUsViVTFE9BPXePCSuOudWBlsv3P3/XPDEsItXYZeXMtScAWg8QMDljH4BWLWeiNUBJAQJBAPhPq5L5Q2WTX8BN6/h6ALQsPRkzzixAaaFptTI0xalLAKhtROzT/gyaDAbVJ2OrVVOVjq5r4PwlYcgngJipo4ECQQC9efA7abKVwCAehEQGvLl5IENZjnhynqj6JGetu+W9tIGM6Mvnl0qCZ6pRt4liUkjyvoVSbA1Wx5o6gBMAWMz/AkEA6NpFSekn04wVAY/Q1c0K9ep6TTTwhTzOEYjAW1lzdmMYNdlqBjByDz0yRAIFEwkrVdts1pHhhHJFtN2eUQrbgQJAZn/ZinpyZnpJpdiNaEud1j2y0Xa2B+N2q+MXxy9XaBmG37Br5fu9DfCAfbZY9rxmxBJ20xCq4qAV+cGhBdYEBwJANtTs0VQyjbxn3dIl55qzeECxfvK1WegYitPXzZcpYL24sdP++BUX24nk9N1i2Hz4myjcrcPbXjYvC+vCpWh0tg==";

    private String token;

    /*@Test
    public void testRsa() throws Exception {
        RSAUtil.genKeyPair();
    }

    @Before
    public void testGetRsa() throws Exception {
        RSAUtil.genKeyPair();
        this.publicKey = RSAUtil.keyMap.get(0);
        this.privateKey =  RSAUtil.keyMap.get(1);
    }*/

    @Test
    public void testGenerateToken() throws Exception {
       /* RSAUtil.genKeyPair();
        this.publicKey = RSAUtil.keyMap.get(0);
        this.privateKey =  RSAUtil.keyMap.get(1);*/

        String domainForUrl = RequestUtil.getDomainForUrl("www.abc.com.tabo.com");
        System.out.println(domainForUrl);

        /*RSAUtil.genKeyPair(1024,pubKeyPath,priKeyPath);

        publicKey = FileUtil.readFromFile(pubKeyPath).get(0);
        privateKey = FileUtil.readFromFile(priKeyPath).get(0);
        System.out.println("publicKey:" + publicKey);
        System.out.println("privateKey:" + privateKey);
        // 生成token
        String token = createJWT(60,new UserInfo(20L, "jack","123","1451","183"), privateKey);
        System.out.println("token = " + token);
        this.token = token;*/
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIyMCIsInBhc3N3b3JkIjoiMTIzIiwicGhvbmUiOiIxODMiLCJuYW1lIjoiamFjayIsImlkIjoiMjAiLCJleHAiOjE1NDgwNTQ0NDUsImNsYXNzIjoiY2xhc3MgY29tLnp6cnEuZHRvLlVzZXJJbmZvIiwiaWF0IjoxNTQ4MDU0Mzg1LCJlbWFpbCI6IjE0NTEiLCJqdGkiOiJhNDRkNTdjZC02YTAzLTQ2NWQtOGE1ZS0wYWU1MWM5MDIwNmIifQ.c1uWlD4mARAAyQfl0hWVE4JXz5JiaYU2aWMx1MokmtyyhMqCq1BBCqq25WHDtXllBwGTJPjLoEkDMtSIpfMyLu9DFhpvbb3VIrt_DlL2Q-LY-TU5wos0a_A5-7TanWPsO1P5gKSLOmsjTHIgjvkpYhb_E1PlaWaqPA7A4oj5vBg";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3yRhX7DrNbMBOjBAvTwYKfmYjole9O8yC0w57HhGPfm/7moGlt8tD66giJOnNJftoDw20laAxem6CZ38L5L5nAQJHtmnDUNjcs+1XysV0npK1yNeM0CBQ8Z1g/1+PcGylIjRp+eiV0xW1k9PkmWCJboLH+3HO3OdFn++ic5ypfwIDAQAB";
        // 解析token
        Claims claims = parseJWT(token, publicKey);
        System.out.println("id: " +claims);
        System.out.println("userName: " + claims);
    }

    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param seconds jwt过期时间
     * @param userInfo      登录成功的user对象
     * @return
     */
    public static String createJWT(long seconds, UserInfo userInfo, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<String, Object>();
        Map<String, String> map = BeanUtils.describe(userInfo);
        claims.putAll(map);

        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，这个秘钥不能外露。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了
        RSAPrivateKey priKey = RSAUtil.getPriKey(privateKey);

        //生成签发人
        String subject = userInfo.getId().toString();


        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, priKey);
        if (seconds >= 0) {
            long expMillis = nowMillis + seconds*1000;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /**
     * Token的解密
     * @param token 加密后的token
     * @param publicKey  公钥
     * @return
     */
    public static Claims parseJWT(String token, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //签名秘钥，和生成的签名的秘钥一模一样
        RSAPublicKey pubKey = RSAUtil.getPubKey(publicKey);
        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(pubKey)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }


    /**
     * 校验token
     * 在这里可以使用官方的校验，我这里校验的是token中携带的密码于数据库一致的话就校验通过
     * @param token
     * @param userInfo
     * @return
     */
    public static Boolean isVerify(String token, UserInfo userInfo) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = userInfo.getPassword();

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();

        if (claims.get("password").equals(userInfo.getPassword())) {
            return true;
        }

        return false;
    }
}
