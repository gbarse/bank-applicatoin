package com.iunetworks;

import com.iunetworks.bootstrap.UserManagementApplicationBootstrap;
import com.iunetworks.entities.BankUser;
import com.iunetworks.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;


@SpringBootApplication
@EnableEurekaClient
public class BankUserManagementApplication implements CommandLineRunner {

  private final RoleRepository repository;
  private final BankUserRepository bankUserRepository;
  private final PasswordEncoder passwordEncoder;


  private UserManagementApplicationBootstrap bootstrap;

  public BankUserManagementApplication(RoleRepository repository, BankUserRepository bankUserRepository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.bankUserRepository = bankUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  public void setBootstrap(UserManagementApplicationBootstrap bootstrap) {
    this.bootstrap = bootstrap;
  }

  public static void main(String[] args) {
    SpringApplication.run(BankUserManagementApplication.class, args);
  }

  @Override
  public void run(String... args) {
    this.bootstrap.init();

    BankUser superAdminRole = new BankUser("top", "bestadmin@mail.ru", "admin1",
      "Zaqyan street", "091011111", "Yerevan",
      LocalDate.of(2015, Month.MARCH, 29), passwordEncoder.encode("admin123"));


    final Role role = this.repository.findSuperAdminRole().get();
    superAdminRole.setRoles(Set.of(role));

    bankUserRepository.save(superAdminRole);
  }
}
