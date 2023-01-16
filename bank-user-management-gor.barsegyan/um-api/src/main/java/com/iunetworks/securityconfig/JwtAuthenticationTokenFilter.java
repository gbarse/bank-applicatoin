package com.iunetworks.securityconfig;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
  private final JwtTokenUtil tokenUtil;

  private final UserDetailsService userDetailsService;
  private static final Logger log = LoggerFactory.getLogger(User.class);

  public JwtAuthenticationTokenFilter(final JwtTokenUtil tokenUtil, UserDetailsService userDetailsService) {
    this.tokenUtil = tokenUtil;
    this.userDetailsService = userDetailsService;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {



    String authorizationHeader = request.getHeader(AUTHORIZATION);

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        String token = authorizationHeader.substring("Bearer ".length());

        List<String> permissions = tokenUtil.getClaimFromToken(token, this.retrievePermissions());

        String username = tokenUtil.getUsernameFromToken(token);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

//        User user = (User) this.userDetailsService.loadUserByUsername(username);

        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(new CurrentUser(username, permissions), null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

      } catch (Exception exception) {
        log.error("exception: {}", exception.getMessage());
        response.setHeader("exception", exception.getMessage());
        response.sendError(FORBIDDEN.value());
      }

    } else {
      filterChain.doFilter(request, response);
    }
  }



  private Function<Claims, List<String>> retrievePermissions() {
    return claims -> (List<String>) claims.get("permissions");
  }

  private static class CurrentUser implements UserDetails {

    private final String username;
    private final List<String> permissions;

    public CurrentUser(final String username, final Collection<String> permissions) {
      this.username = username;
      this.permissions = new ArrayList<>(permissions);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
      return null;
    }

    @Override
    public String getUsername() {
      return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }
  }
}

