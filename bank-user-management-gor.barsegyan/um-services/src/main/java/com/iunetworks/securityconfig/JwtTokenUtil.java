package com.iunetworks.securityconfig;

import com.iunetworks.entities.BankUser;
import com.iunetworks.entities.CustomerUser;
import com.iunetworks.entities.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private Long expiration;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
      .setSigningKey(secret)
      .parseClaimsJws(token)
      .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public List<String> generateCustomerToken(UserPrincipal user) {
    return doGenerateCToken((CustomerUser) user);
  }

  public List<String> generateBankToken(UserPrincipal user) {
    return doGenerateBToken((BankUser) user);
  }


  public List<String> doGenerateCToken(CustomerUser user) {
    final Date createdDate = new Date();
    final Date expirationDate = calculateExpirationDate(createdDate);
    final Date refreshExpirationDate = calculateRefreshExpirationDate(createdDate);

    Claims claims = Jwts.claims().setSubject(user.username());
    claims.put("permissions", user.authorities());
    claims.put("id",user.getId().toString());


    String accessToken = Jwts.builder()
      .setClaims(claims)
//      .setIssuer(user.getId().toString())
      .setSubject(user.username())
      .setIssuedAt(createdDate)

      .setExpiration(expirationDate)
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();

    String refresh_token = Jwts.builder()
      .setClaims(claims)
//      .setIssuer(user.getId().toString())
      .setSubject(user.username())
      .setIssuedAt(createdDate)
      .setExpiration(refreshExpirationDate)
      .signWith(SignatureAlgorithm.HS256, secret)
      .compact();

    List<String> tokens = new ArrayList<>();
    tokens.add(accessToken);
    tokens.add(refresh_token);
    return tokens;

  }

  public List<String> doGenerateBToken(BankUser user) {
    final Date createdDate = new Date();
    final Date expirationDate = calculateExpirationDate(createdDate);
    final Date refreshExpirationDate = calculateRefreshExpirationDate(createdDate);

    Claims claims = Jwts.claims().setSubject(user.username());
    claims.put("permissions", user.authorities());
    claims.put("id",user.getId().toString());



    String accessToken = Jwts.builder()
      .setClaims(claims)
      .setSubject(user.username())
//      .setIssuer(user.getId().toString())
      .setIssuedAt(createdDate)
      .setExpiration(expirationDate)
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();

    String refresh_token = Jwts.builder()
      .setClaims(claims)
      .setSubject(user.username())
//      .setIssuer(user.getId().toString())
      .setIssuedAt(createdDate)
      .setExpiration(refreshExpirationDate)
      .signWith(SignatureAlgorithm.HS256, secret)
      .compact();

    List<String> tokens = new ArrayList<>();
    tokens.add(accessToken);
    tokens.add(refresh_token);
    return tokens;

  }

//  public Boolean validateToken(String token, String username) {
//    final String usernameToken = getUsernameFromToken(token);
//    return (
//      usernameToken.equals(username)
//        && !isTokenExpired(token));
//  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 2000);
  }

  private Date calculateRefreshExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 15000);
  }
}
