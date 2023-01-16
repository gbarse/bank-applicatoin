package com.iunetworks.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "t_bank_users")
public class BankUser extends User implements UserPrincipal {

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "t_bank_users_role",
    joinColumns = @JoinColumn(name = "bank_user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public BankUser() {

  }


  public Set<Role> getRoles() {
    return roles;
  }
  public void addRoles(Collection<Role> roles){
    this.roles.addAll(roles);
  }

  public BankUser(String lastName, String email, String firstName, String address, String phoneNumber, String city, LocalDate birthDate, String password) {
    super(lastName, email, firstName, address, phoneNumber, city, birthDate, password);

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
