package com.iunetworks.repositories;

import com.iunetworks.entities.LegalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LegalAccountRepository extends JpaRepository<LegalAccount, UUID> {
    boolean existsByUserId(UUID id);

  List<LegalAccount> findAllByDeletedIsNull();

  boolean existsByIdAndDeletedIsNull(UUID id);

  Optional<Object> findByIdAndDeletedIsNull(UUID id);

  boolean existsByAccountNumberAndDeletedIsNull(String accountNumber);

  Optional<LegalAccount> findByAccountNumberAndDeletedIsNull(String accountNumber);

  @Query("SELECT b FROM LegalAccount b WHERE b.status = 'IN_PROCESS'")
  List<LegalAccount> getAllByInProcessAccount();

//  @Query("SELECT a.amount, a.accountNumber, a.currency FROM  LegalAccount a WHERE a.accountNumber="aaa"")
//  List<LegalAccount> findByAccountNumber("aaa"String accountNumber);
}
