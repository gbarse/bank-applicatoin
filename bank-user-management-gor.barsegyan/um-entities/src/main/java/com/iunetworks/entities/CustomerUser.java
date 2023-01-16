package com.iunetworks.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "t_customer_users")

public class CustomerUser extends User implements UserPrincipal {

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "t_customer_users_role",
    joinColumns = @JoinColumn(name = "customer_user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public Set<Role> getRoles() {
    return roles;
  }

  public void addRole(Collection<Role> roles){
    this.roles.addAll(roles);
  }
  public void removeRoles(Collection<Role> roles){
    this.roles.removeAll(roles);
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Override
  public Set<String> authorities() {
    final Set<String> permissions = new HashSet<>();

    this.roles.forEach(r -> permissions.addAll(
      r.getPermission().stream().map(Permission::getName).collect(Collectors.toList())
    ));

    return permissions;

  }
  @Override
  public String username() {
    return this.email;
  }

  @Override
  public String password() {
    return this.password;
  }
}
