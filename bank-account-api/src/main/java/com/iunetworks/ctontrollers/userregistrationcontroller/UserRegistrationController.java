package com.iunetworks.ctontrollers.userregistrationcontroller;


import com.iunetworks.userservice.UserService;
import com.iunetworks.dtos.userdto.UserRegistrationRequestDto;
import com.iunetworks.dtos.userdto.UserRegistrationResponsetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")

public class UserRegistrationController {

  private final UserService userService;

  @Autowired
  public UserRegistrationController(UserService userService) {
    this.userService = userService;
  }


  @PatchMapping("/create")
  public UserRegistrationResponsetDto createUser(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
    return userService.create(userRegistrationRequestDto);
  }
}
