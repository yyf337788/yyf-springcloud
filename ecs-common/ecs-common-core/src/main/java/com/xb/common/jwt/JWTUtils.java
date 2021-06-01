package com.xb.common.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xb.common.base.ret.ApiResult;
import com.xb.common.entity.model.member.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 基于JWT生成和解析token的工具类
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
public class JWTUtils {

    /**token密钥，不能泄露给用户*/
    public static final String SECRET = "XBKTILoveYou1314";
    /**token失效时间：7天*/
    public static final int TOKEN_EXP =1000*60*60*24*7;

    /**
     * 创建token
     * @param user
     * @return
     */
    public static ApiResult createToken(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("type","JWT");
        try {
            String token = JWT.create().withHeader(map)
                    .withClaim("iss","Server")
                    .withClaim("aud","APP")
                    .withClaim("user",null == user ? null : JSON.toJSONString(user))
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXP))
                    .sign(Algorithm.HMAC256(SECRET));
            return ApiResult.success().put("token",token);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("创建token失败");
        }
    }

    /**
     * 解析token，获取User对象
     * @param token
     * @return
     */
    public static ApiResult verifyToken(String token){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ApiResult.error("登录凭证参数非法");
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return ApiResult.error("登录凭证已过期,请重新登录");
        }

        //获取user
        if(jwt == null || jwt.getClaim("user") == null){
            return ApiResult.error("用户登录信息验证失败");
        }
        return ApiResult.success().put("user",jwt.getClaim("user").asString());
    }

    public static void main(String[] args) {
        User user = new User();
        user.setPhoneNumber("15565765456");
        user.setEmail("23478@163.com");
        user.setName("jll");
        user.setPassword("2345677");

        ApiResult result = createToken(user);
        System.out.println("token:  "+result);

        ApiResult result1 = verifyToken(result.getResult().get("token").toString());
        System.out.println("user:   "+result1);


    }
}
