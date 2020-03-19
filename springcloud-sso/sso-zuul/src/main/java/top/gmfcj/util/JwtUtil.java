package top.gmfcj.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Component
public class JwtUtil {

    // 解密的密钥 加密的时候传入密钥 解密的时候使用密钥来解析
    public static final String JWT_KEY = "login_key";

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKeySpec generalKey() {
        String stringKey = JWT_KEY;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Map<String, Object> generalKeyMap() {
        String stringKey = JWT_KEY;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        Map<String, Object> map = new HashMap();
        map.put("arg1", encodedKey);
        map.put("arg2", encodedKey.length);
        map.put("arg3", "AES");
        return map;
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param subject
     * @param ttlMillis 过期的时间长度
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, String subject, long ttlMillis) throws Exception {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        // 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，这个秘钥不能外露。
        // 它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
//                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
                .setId(id)
                // iat: jwt的签发时间
                .setIssuedAt(now)
                // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            // 设置过期时间
            builder.setExpiration(exp);
        }
        //就开始压缩为xxx.xxx.xxx这样的jwt
        return builder.compact();
    }


    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> user = new HashMap<>();
        user.put("username", "123");
        user.put("password", "321");
        //把数据转换为JWT token
//        String jwt = createJWT(UUID.randomUUID().toString(), objectMapper.writeValueAsString(user), 3600 * 24);
//
//        System.out.println("加密后的" + jwt);
//        //解密
//        Claims claims = parseJWT(jwt);
//
//        System.out.println("解密后的" + claims.getSubject());


        System.out.println(objectMapper.writeValueAsString(user));

        ArrayList<Object> list = new ArrayList<>();
        list.add(user);
        list.add(user);
        list.add(user);
        System.out.println(objectMapper.writeValueAsString(list));
    }


    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        // 签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(key)
                // 设置需要解析的jwt
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }


}