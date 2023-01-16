package com.iunetworks.userdetailsserivce;


import com.iunetworks.BankUserRepository;
import com.iunetworks.CustomerUserRepository;
import com.iunetworks.entities.BankUser;
import com.iunetworks.entities.CustomerUser;
import com.iunetworks.entities.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class UserDetailsServiceImpl implements UserDetailsService {

  private final BankUserRepository bankUserRepository;
  private final CustomerUserRepository customerUserRepository;
  private final ModelMapper modelMapper;

  @Autowired
  UserDetailsServiceImpl(BankUserRepository bankUserRepository, CustomerUserRepository customerUserRepository, ModelMapper modelMapper) {
    this.bankUserRepository = bankUserRepository;
    this.customerUserRepository = customerUserRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Set<Permission> permissionSet = new HashSet<>();

    if (customerUserRepository.existsByEmailAndDeletedIsNull(email)) {
      CustomerUser user = customerUserRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Wrong username"));

      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      user.getRoles().forEach(t -> permissionSet.addAll(t.getPermission()));
      permissionSet.stream()
        .map(p -> new SimpleGrantedAuthority(p.getName()))
        .collect(Collectors.toList());

      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.password(), authorities);
    } else if (bankUserRepository.existsByEmailAndDeletedIsNull(email)) {
      BankUser user = bankUserRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Wrong username"));

      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      user.getRoles().forEach(t -> permissionSet.addAll(t.getPermission()));
      permissionSet.stream()
        .map(p -> new SimpleGrantedAuthority(p.getName()))
        .collect(Collectors.toList());

      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.password(), authorities);
    } else throw new UsernameNotFoundException("Wrong email");
  }
}