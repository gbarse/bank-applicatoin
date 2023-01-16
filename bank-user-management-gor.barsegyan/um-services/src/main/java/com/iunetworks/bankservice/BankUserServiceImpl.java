package com.iunetworks.bankservice;

import com.iunetworks.BankUserRepository;
import com.iunetworks.RoleRepository;
import com.iunetworks.customerservice.CustomerUserServiceImpl;
import com.iunetworks.dtos.bankuserdtos.BankUserRegistrationRequestDto;
import com.iunetworks.dtos.bankuserdtos.BankUserRegistrationResponseDto;
import com.iunetworks.dtos.bankuserdtos.BankUserUpdateDto;
import com.iunetworks.dtos.logindto.LoginRequest;
import com.iunetworks.entities.BankUser;
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

import java.time.LocalDateTime;
import java.util.*;


@Service
public class BankUserServiceImpl implements BankUserService {

  private final BankUserRepository bankUserRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder encoder;
  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtTokenUtil jwtTokenUtil;

  private static final Logger log = LoggerFactory.getLogger(CustomerUserServiceImpl.class);


  @Autowired
  public BankUserServiceImpl(BankUserRepository bankUserRepository, ModelMapper modelMapper, PasswordEncoder encoder, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
    this.bankUserRepository = bankUserRepository;
    this.modelMapper = modelMapper;
    this.encoder = encoder;
    this.roleRepository = roleRepository;

    this.passwordEncoder = passwordEncoder;
    this.jwtTokenUtil = jwtTokenUtil;
  }


  @Override
  public BankUserRegistrationResponseDto addBankUser(BankUserRegistrationRequestDto bankUserRegistrationRequestDto) {
    log.info("Bank user has tried to register a new user");
    Optional<BankUser> emailEntry = bankUserRepository.findByEmail(bankUserRegistrationRequestDto.getEmail());
    if (emailEntry.isPresent()) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "email already exists!");
    }
    if (!Objects.equals(bankUserRegistrationRequestDto.getPassword(), bankUserRegistrationRequestDto.getMatchingPassword())) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "passwords do not match");
    }
    BankUser savedUser = modelMapper.map(bankUserRegistrationRequestDto, BankUser.class);
    savedUser.setPassword(encoder.encode(savedUser.getPassword()));
    final Role role = this.roleRepository.findAdminRole()
      .orElseThrow(() -> new RuntimeException("Something went wrong"));
    savedUser.setRoles(Set.of(role));
    log.info("The bank user has been saved ");
    bankUserRepository.save(savedUser);
    return modelMapper.map(savedUser, BankUserRegistrationResponseDto.class);
  }

  @Override
  public BankUser saveUser(BankUser bankUser) {
    bankUser.setPassword(encoder.encode(bankUser.getPassword()));
    return bankUserRepository.save(bankUser);
  }


  @Override
  public void deleteBankUser(UUID id) {

    BankUser user = bankUserRepository.findById(id).get();
    if (user.getDeleted() != null) {
      throw new ResourceNotFoundException(id);
    }
    user.setDeleted(LocalDateTime.now());
    bankUserRepository.save(user);
    log.info("The bank user has been deleted");
  }


  @Override
  public void recoverBankUser(UUID id) {
    BankUser user = bankUserRepository.findById(id).get();
    log.info("bank user is trying to recover another bank user");
    if (user.getDeleted() == null) {
      throw new ResourceNotFoundException(id);
    }
    user.setDeleted(null);
    bankUserRepository.save(user);
    log.info("The bank user has been recovered");
  }


  @Override
  public List<BankUserRegistrationResponseDto> getAllBankUsers() {
    List<BankUser> all = bankUserRepository.findAllByDeletedIsNull();
    log.info("The list with all bank users has been created");
    List<BankUserRegistrationResponseDto> allUserDto = new ArrayList<>();
    for (BankUser user :
      all) {
      BankUserRegistrationResponseDto userDto = modelMapper.map(user, BankUserRegistrationResponseDto.class);
      allUserDto.add(userDto);
    }
    return allUserDto;
  }


  @Override
  public BankUserRegistrationResponseDto getBankUserById(UUID id) {
    log.info("Bank User has tried to get an information about a bank user");
    if (!bankUserRepository.existsByIdAndDeletedIsNull(id)) {
      throw new ResourceNotFoundException(id);
    }
    return modelMapper
      .map((bankUserRepository.findByIdAndDeletedIsNull(id)
        .orElseThrow(() -> new ResourceNotFoundException(id))), BankUserRegistrationResponseDto.class);
  }

  @Override
  public BankUserUpdateDto updateBankUser(UUID id, BankUserUpdateDto bankUserUpdateDto) {
    log.info("Bank User has tried to update information about a bank user");
    if (!bankUserRepository.existsByIdAndDeletedIsNull(id)) {
      throw new ResourceNotFoundException(id);
    }
    BankUser user = modelMapper.map(bankUserRepository.findById(id), BankUser.class);
    Optional.ofNullable(bankUserUpdateDto.getFirstName()).ifPresent(user::setFirstName);
    Optional.ofNullable(bankUserUpdateDto.getLastName()).ifPresent(user::setLastName);
    Optional.ofNullable(bankUserUpdateDto.getAddress()).ifPresent(user::setAddress);
    Optional.ofNullable(bankUserUpdateDto.getCity()).ifPresent(user::setCity);
    Optional.ofNullable(bankUserUpdateDto.getBirthDate()).ifPresent(user::setBirthDate);
    Optional.ofNullable(bankUserUpdateDto.getPhoneNumber()).ifPresent(user::setPhoneNumber);
    Optional.ofNullable(bankUserUpdateDto.getEmail()).ifPresent(user::setEmail);

    if (bankUserUpdateDto.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(bankUserUpdateDto.getPassword()));
    }

    bankUserRepository.save(user);
    log.info("The bank user info has been updated");
    return modelMapper.map(user, BankUserUpdateDto.class);
  }

  @Override
  public ResponseEntity<?> signIn(LoginRequest source) {
    BankUser bankUser = bankUserRepository.findByEmailAndDeletedIsNull(source.getEmail()).orElseThrow(
      () -> new RuntimeException("user with email not found")
    );

    if (!passwordEncoder.matches(source.getPassword(), bankUser.getPassword())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Wrong password or email");
    }
    return new ResponseEntity<>(jwtTokenUtil.generateBankToken(bankUser), HttpStatus.OK);
  }
}








