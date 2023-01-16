package com.iunetworks.customerservice;


import com.iunetworks.CustomerUserRepository;
import com.iunetworks.RoleRepository;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationRequestDto;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationResponseDto;
import com.iunetworks.dtos.customeruserdtos.CustomerUserUpdateDto;
import com.iunetworks.dtos.logindto.LoginRequest;
import com.iunetworks.entities.CustomerUser;
import com.iunetworks.entities.Role;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.securityconfig.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class CustomerUserServiceImpl implements CustomerUserService {

  private final CustomerUserRepository customerUserRepository;
  private final ModelMapper modelMapper;
  private static final Logger log = LoggerFactory.getLogger(CustomerUserServiceImpl.class);
  private final RoleRepository roleRepository;

  private final PasswordEncoder encoder;

  private final JwtTokenUtil jwtTokenUtil;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public CustomerUserServiceImpl(CustomerUserRepository customerUserRepository, ModelMapper modelMapper, RoleRepository roleRepository, PasswordEncoder encoder, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
    this.customerUserRepository = customerUserRepository;
    this.roleRepository = roleRepository;
    this.modelMapper = modelMapper;
    this.encoder = encoder;
    this.jwtTokenUtil = jwtTokenUtil;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public CustomerUserRegistrationResponseDto addCustomerUser(CustomerUserRegistrationRequestDto request) {
    log.info("Customer has tried to register an account");
    Optional<CustomerUser> emailEntry = customerUserRepository.findByEmail(request.getEmail());
    if (emailEntry.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exists!");
    }
    if (!Objects.equals(request.getPassword(), request.getMatchingPassword())) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "passwords do not match");
    }

    CustomerUser savedUser = modelMapper.map(request, CustomerUser.class);
    savedUser.setPassword(encoder.encode(savedUser.getPassword()));
    final Role role = this.roleRepository.findCustomerRole()
      .orElseThrow(() -> new RuntimeException("Something went wrong"));
    savedUser.setRoles(Set.of(role));
    customerUserRepository.save(savedUser);
    return modelMapper.map(savedUser, CustomerUserRegistrationResponseDto.class);
  }


  @Override
  public CustomerUserUpdateDto update(UUID id, CustomerUserUpdateDto customerUserUpdateDto) {
    log.info("User has tried to update information about a customer");

    if (!customerUserRepository.existsByIdAndDeletedIsNull(id)) {
      throw new ResourceNotFoundException(id);
    }

    CustomerUser user = modelMapper.map(customerUserRepository.findById(id), CustomerUser.class);
    Optional.ofNullable(customerUserUpdateDto.getFirstName()).ifPresent(user::setFirstName);
    Optional.ofNullable(customerUserUpdateDto.getLastName()).ifPresent(user::setLastName);
    Optional.ofNullable(customerUserUpdateDto.getAddress()).ifPresent(user::setAddress);
    Optional.ofNullable(customerUserUpdateDto.getCity()).ifPresent(user::setCity);
    Optional.ofNullable(customerUserUpdateDto.getBirthDate()).ifPresent(user::setBirthDate);
    Optional.ofNullable(customerUserUpdateDto.getPhoneNumber()).ifPresent(user::setPhoneNumber);
    Optional.ofNullable(customerUserUpdateDto.getEmail()).ifPresent(user::setEmail);
    if (customerUserUpdateDto.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(customerUserUpdateDto.getPassword()));
    }
    customerUserRepository.save(user);
    log.info("The changes have been saved");
    return modelMapper.map(user, CustomerUserUpdateDto.class);
  }

  public ResponseEntity<?> signIn(LoginRequest source) {
    CustomerUser customerUser = customerUserRepository.findByEmailAndDeletedIsNull(source.getEmail()).orElseThrow(
      () -> new RuntimeException("user with email not found")
    );


    if (!passwordEncoder.matches(source.getPassword(), customerUser.getPassword())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Wrong password or email");
    }
    return new ResponseEntity<>(jwtTokenUtil.generateCustomerToken(customerUser), HttpStatus.OK);
  }


  public CustomerUserRegistrationResponseDto getByEmail(String username) {
    return modelMapper.map(customerUserRepository.findByEmailAndDeletedIsNull(username), CustomerUserRegistrationResponseDto.class);
  }
}













