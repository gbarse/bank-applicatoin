package com.iunetworks.secutriyconfig;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CurrentUser implements UserDetails {
  private UUID id;
  private final String username;
  private final List<String> permissions;

  public CurrentUser(final UUID id, final String username, final Collection<String> permissions) {
    this.username = username;
    this.id = id;
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

  public UUID getId() {
    return id;
  }
}

