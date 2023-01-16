package com.iunetworks.entities;

import java.util.Set;

public interface UserPrincipal {

  Set<String> authorities();

  String username();

  String password();




}
