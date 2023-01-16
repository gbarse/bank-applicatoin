package com.iunetworks;


import com.iunetworks.entities.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankUserRepository extends JpaRepository<BankUser, UUID> {


  boolean existsByIdAndDeletedIsNull(UUID id);

  Optional<BankUser> findByEmail(String email);

  boolean existsById(UUID id);
//  boolean existsByEmail(String email);


  Optional<BankUser> findByIdAndDeletedIsNull(UUID userId);

  Optional<BankUser> findByEmailAndDeletedIsNull(String email);

  boolean existsByEmailAndDeletedIsNull(String email);

  List<BankUser> findAllByDeletedIsNull();

}
