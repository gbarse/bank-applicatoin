package com.iunetworks.config;

import com.iunetworks.entities.secutriyconfig.CurrentUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;


public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
  @Value("${jwt.secret}")
  private String secret;

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
      .setSigningKey(secret)
      .parseClaimsJws(token)
      .getBody();
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public UUID getIdFromToken(String token) {
    Claims claims = getAllClaimsFromToken(token);
    return UUID.fromString(claims.get("id").toString());
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String requestHeader = request.getHeader("Authorization");
    String username = null;
    String authToken = null;
    UUID loggedId = null;
    if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
      authToken = requestHeader.substring(7);
      try {
        List<String> permissions = getClaimFromToken(authToken, this.retrievePermissions());
        username = getUsernameFromToken(authToken);
        loggedId = getIdFromToken(authToken);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(new CurrentUser(loggedId, username, permissions), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
      } catch (Exception e) {
        logger.error(e);
      }
    } else {
      filterChain.doFilter(request, response);
    }

  }

  private Function<Claims, List<String>> retrievePermissions() {
    return claims -> (List<String>) claims.get("permissions");
  }

}

