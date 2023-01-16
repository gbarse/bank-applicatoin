package com.iunetworks.dtos.roledtos;

import javax.validation.constraints.NotNull;

public class RoleDto {


  @NotNull
  private String name;


  public RoleDto() {

  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}




