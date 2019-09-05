package com.midea.epm.common.util;

import com.google.common.collect.Maps;
import com.midea.epm.common.config.Global;
import com.midea.epm.common.entity.JWTResult;
import com.midea.epm.common.entity.ResponseCode;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

/**
 * jwt身份验证工具类
 */
public class JWTUtils {

    private static RSAPrivateKey priKey;
    private static RSAPublicKey pubKey;
    private static Long defaultExp=Long.valueOf(Global.getConfig("security.token.expire"));

    // 请求头
    public static final String AUTH_HEADER = "X-Authorization-With";

    private static class SingletonHolder {
        private static final JWTUtils INSTANCE = new JWTUtils();
    }

    public synchronized static JWTUtils getInstance() {
        if (priKey == null && pubKey == null) {
            priKey = RSAUtils.getPrivateKey(RSAUtils.modulus, RSAUtils.private_exponent);
            pubKey = RSAUtils.getPublicKey(RSAUtils.modulus, RSAUtils.public_exponent);
        }
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取token
     * @param uid
     * @param exp 过期时间，单位分钟
     * @return
     */
    public  String getToken(String uid,int exp){
        long endTime=System.currentTimeMillis()+1000*60*exp;
        return Jwts.builder().setSubject(uid).setIssuedAt(new Date()).setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.RS512, priKey).compact();
    }


    public  String getToken(String uid,String username,int exp){
        long endTime=System.currentTimeMillis()+1000*60*exp;
        Map<String,Object> map =Maps.newHashMap();
        map.put("username",username);
        return Jwts.builder().setClaims(map).setSubject(uid).setIssuedAt(new Date()).setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.RS512, priKey).compact();
    }



    /**
     * 获取token
     * @param uid
     * @return
     */
    public  String getToken(String uid){
        long endTime=System.currentTimeMillis()+1000*60*defaultExp;
        return Jwts.builder().setSubject(uid).setIssuedAt(new Date()).setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.RS512, priKey).compact();
    }

    /**
     * 检查Token是否合法
     * @param token
     * @return JWTResult
     */
    public JWTResult checkToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token).getBody();
            String sub = claims.get("sub", String.class);
            return new JWTResult(true, sub, "合法请求", ResponseCode.SUCCESS.getCode());
        } catch (ExpiredJwtException e) {
            // 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
            return new JWTResult(false, null, "token已过期", ResponseCode.TOKEN_TIMEOUT.getCode());
        } catch (MalformedJwtException e) {
            return  new JWTResult(false, null, "令牌格式错误", ResponseCode.NO_AUTH.getCode());
        }catch (UnsupportedJwtException e) {
            return new JWTResult(false, null, "令牌无效", ResponseCode.NO_AUTH.getCode());
        } catch (SignatureException e) {
            // 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            return new JWTResult(false, null, "令牌签名无效", ResponseCode.NO_AUTH.getCode());
        } catch (Exception e) {
            return new JWTResult(false, null, "令牌错误:", ResponseCode.NO_AUTH.getCode());
        }
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims =null;
        try {
            if(StringUtils.isNotBlank(token)){
                claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token).getBody();
            }
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

}
