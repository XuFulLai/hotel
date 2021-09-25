package group.oneonetwo.hotelintelligencesystem.components.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;


/**
 * @author 文
 * @description token工具类
 */
public class JwtTokenUtils {

    /**
     * 自定义标识
     */
//    @Value("${jwt.header}")
    public final static String TOKEN_HEADER = "Authorization";

    /**
     * 前缀
     */
//    @Value("${jwt.token-prefix}")
    public final static String TOKEN_PREFIX = "Bearer";

    /**
     * 密钥(基于base64)
     */
//    @Value("${jwt.secret}")
    private final static String SECRET = "12d8sxsm+xswxyy+2d8sxswxyybmaik/2dwzmztbdwtb/3rggdxfnjppspaparggdxfnjppspapapa+hbgwjybqyl+++++";

    private static final String ISS = "tomato";

    // 角色的key
    private static final String ROLE_CLAIMS = "rol";

    //过期时间
    private static final long EXPIRATION = 3600000L;

//    // 选择了记住我之后的过期时间为7天
//    private static final long EXPIRATION_REMEMBER = 604800L;

    /**
     * 创建token
     * @author 文
     * @param username
     * @param role
     * @return
     */
    public static String createToken(String username,String role) {
        HashMap<String, Object> map = new HashMap<>();
        //获取当前时间,以计算过期时间
        long time = System.currentTimeMillis();
//        long e = EXPIRATION;
//        long end = time + e;
//        System.out.println(new Date(time));
//        System.out.println(new Date(end));
        map.put(ROLE_CLAIMS, role);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(time + EXPIRATION))
                .compact();
    }

    //

    /**
     * 从token中获取用户名(不需要头部)
     * @author 文
     * @param token
     * @return
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }



    /**
     * 获取用户角色
     * @author 文
     * @param token
     * @return
     */
    public static String getUserRole(String token){
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }


    /**
     * 检查token是否过期
     * @author 文
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 获得token体
     * @author 文
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getTokenHeader() {
        return TOKEN_HEADER;
    }

    public static String getTokenPrefix() {
        return TOKEN_PREFIX;
    }

    public static String getSECRET() {
        return SECRET;
    }

    public static String getISS() {
        return ISS;
    }

    public static String getRoleClaims() {
        return ROLE_CLAIMS;
    }

    public static long getEXPIRATION() {
        return EXPIRATION;
    }

//    public static long getExpirationRemember() {
//        return EXPIRATION_REMEMBER;
//    }
}
