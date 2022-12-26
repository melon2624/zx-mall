package com.zx.user.auth.util;

import com.zx.framework.common.utils.DateUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * @author zhangxin
 * @date 2022/12/13 15:41
 */
@Slf4j
@Component
public class JwtUtils {

    private JwtUtils() {

    }


    public static String generateToken(HashMap<String, Object> user, PrivateKey privateKey) {

      return   Jwts.builder()
                .setClaims(user)
                .setExpiration(DateUtil.toDate(LocalDate.of(2099, 1, 1)))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }
}
