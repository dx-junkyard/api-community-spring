package com.dxjunkyard.community.service;

//import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;


// 認証機能は
// https://github.com/urashin/micro-volunteer-pf/blob/master/src/main/java/org/microvolunteer/platform/service/TokenService.java
// から再利用

@Service
public class AuthService {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    /*
    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private SnsRegisterMapper snsRegisterMapper;
     */

    @Value("${encrypt.jwt.secret}")
    private String jwt_secret;

    @Value("${encrypt.jwt.expire}")
    private Integer jwt_expire;

    public String checkAuthHeader(String authHeader) {
        logger.info("start : check header");
        // Authorizationヘッダーの形式が正しいか確認
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;  // Unauthorized
        }

        logger.info("get token");
        // トークンを取得
        String token = authHeader.substring(7);  // "Bearer "の部分を除く

        logger.info("check token");
        // トークンの正当性をMock関数で検証（実際にはサービス層でJWTの検証を行う）
        String userId = getUserId(token);
        //String userId = validateTokenAndGetUserId(token);
        if (userId == null) {
            return null;  // Unauthorized
        }
        return userId;
    }

    private String validateTokenAndGetUserId(String token) {
        // JWTの検証を行い、正しい場合はユーザーIDを返す
        if (token.equals("valid-token")) {
            return "12345";  // 正常なユーザーIDを返す
        }
        return null;  // トークンが無効な場合はnullを返す
    }

    public String createToken(String user_id) {
        String token = null;
        try {
            Date expDate = new Date();
            expDate.setTime(expDate.getTime() + jwt_expire);

            Algorithm algorithm = Algorithm.HMAC256(jwt_secret);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withExpiresAt(expDate)
                    .withClaim("user_id",user_id)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            logger.error("error : " + exception.getMessage());
            return null;
            //無効なトークンの場合:メッセージは変更する。
        }
        return token;
    }

    public String getUserId(String token) {
        String user_id = null;
        try {
            // 期限&改竄チェック
            Algorithm algorithm = Algorithm.HMAC256(jwt_secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Claim claim = jwt.getClaim("user_id"); // decodeしてuser_idを取得する
            user_id = claim.asString();
        } catch (JWTDecodeException exception){
            logger.error("Invalid token");
            //throw exception;
        } catch (TokenExpiredException exception) {
            logger.error("Token expired");
            //throw exception;
        }
        return user_id;
    }

    public String getTokenByUserId(String user_id) {
        return this.createToken(user_id);
    }

    /* まだ使わない
    public String getLineId(String idToken) {
        String user_id = null;
        try {
            // 期限&改竄チェック
            Algorithm algorithm = Algorithm.HMAC256(client_secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://access.line.me")
                    .build();
            DecodedJWT jwt = verifier.verify(idToken);
            // decodeしてuser_idを取得する
            String sub = jwt.getClaim("sub").asString();
            String name = jwt.getClaim("name").asString();
            //String exp = jwt.getClaim("exp").asString();
            user_id = sub;
        } catch (JWTDecodeException exception){
            logger.error("Invalid token");
            throw exception;
            //Invalid token
        } catch (TokenExpiredException exception) {
            logger.error("Token expired");
            throw exception;
        }
        return user_id;
    }
     */

    public String getTokenFromAuth(String auth) {
        String[] split = auth.split(" ");
        if (split.length != 2) {
            return null;
        }
        return split[0].equals("Bearer") ? split[1] : null;
    }
}
