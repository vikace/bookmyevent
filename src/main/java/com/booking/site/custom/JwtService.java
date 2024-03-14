package com.booking.site.custom;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public static final String SECRET="SAYHELLOTOTHESECRETKEYMOTHERFUCKERS69696969696";
    public String generateToken(String email, Map<String,Object> claims)
    {
        return createToken(email,claims);
    }
    private String createToken(String email,Map<String,Object> claims)
    {
       return Jwts.builder().addClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+2592000000L)).signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

    }
    private Key getSignKey()
    {
        byte[] arr = Decoders.BASE64.decode(SECRET);
       return Keys.hmacShaKeyFor(arr);
    }
    public String getEmail(String token)
    {
        return extractClaims(token,Claims::getSubject);
    }
    public Date getExpiration(String token)
    {
        return extractClaims(token,Claims::getExpiration);
    }
    private <T> T extractClaims(String token, Function<Claims,T> function)
    {
        return function.apply(extractAllClaims(token));
    }
    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }
}
