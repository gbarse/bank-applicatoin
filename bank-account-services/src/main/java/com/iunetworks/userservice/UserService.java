package com.iunetworks.userservice;

import com.iunetworks.dtos.userdto.UserRegistrationRequestDto;
import com.iunetworks.dtos.userdto.UserRegistrationResponsetDto;
import com.iunetworks.entities.User;
import com.iunetworks.repositories.UserRepository;
import com.iunetworks.secutriyconfig.CurrentUser;
import com.iunetworks.userservice.userserviceinterface.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserService implements IUserService {


  private final UserRepository userRepository;
  private final ModelMapper modelMapper;


  @Autowired
  public UserService(UserRepository userRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;


  }
  @Override
  public UserRegistrationResponsetDto create(UserRegistrationRequestDto userRegistrationRequestDto) {
    User savedUser = modelMapper.map(userRegistrationRequestDto, User.class);
    UUID loggedId;
    try {
      loggedId = ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    } catch (Exception e) {
      throw new ApplicationContextException("JWT is missing");
    }
    savedUser.setLoggedId(loggedId);

    if (userRepository.existsUserByLoggedIdAndDeletedIsNull(loggedId))
      throw new ApplicationContextException("user is already present");

    userRepository.save(savedUser);
    return modelMapper.map(savedUser, UserRegistrationResponsetDto.class);
  }

  @Override
  public void delete(UUID id) {

    User user = userRepository.findById(id).get();
    if (user.getDeleted() != null) {
      throw new ApplicationContextException("THE ACCOUNT IS DELETED");
    }
    user.setDeleted(LocalDateTime.now());
    userRepository.save(user);
  }

  @Override
  public List<UserRegistrationResponsetDto> getAllAccounts() {
    List<User> all = userRepository.findAllByDeletedIsNull();
    List<UserRegistrationResponsetDto> allAccountsDto = new ArrayList<>();
    for (User user :
      all) {
      UserRegistrationResponsetDto userDto = modelMapper.map(user, UserRegistrationResponsetDto.class);
      allAccountsDto.add(userDto);
    }
    return allAccountsDto;
  }
  @Override
  public UserRegistrationResponsetDto getAccountById(UUID id) {

    if (!userRepository.existsByIdAndDeletedIsNull(id)) {
      throw new ApplicationContextException("WRONG ID");
    }
    return modelMapper
      .map((userRepository.findByIdAndDeletedIsNull(id)
        .orElseThrow(() -> new ApplicationContextException("WRONG ID 2"))), UserRegistrationResponsetDto.class);
  }
}
