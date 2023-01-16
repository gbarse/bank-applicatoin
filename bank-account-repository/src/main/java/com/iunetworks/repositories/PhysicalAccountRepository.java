package com.iunetworks.repositories;

import com.iunetworks.entities.PhysicalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysicalAccountRepository extends JpaRepository<PhysicalAccount, UUID> {

  boolean existsUserByIdAndAndDeletedIsNull(UUID userId);

  boolean existsByUserId(UUID id);

  List<PhysicalAccount> findAllByDeletedIsNull();



  Optional<Object> findByIdAndDeletedIsNull(UUID id);
  boolean existsByIdAndDeletedIsNull(UUID id);

  boolean existsByUserIdAndDeletedIsNull(UUID id);

  @Query("SELECT b FROM PhysicalAccount b WHERE b.accountNumber = :account AND b.status = 'IN_PROCESS'")
  Optional<PhysicalAccount> findByAccountNumberAndCardAccountStatus(@Param("account")Long accountNumber);

  @Query("SELECT b FROM PhysicalAccount b WHERE b.status = 'IN_PROCESS'")
  List<PhysicalAccount> getAllByInProcessAccount();

    boolean existsByAccountNumberAndDeletedIsNull(String toAccountNumber);

  Optional<PhysicalAccount> findByAccountNumberAndDeletedIsNull(String toAccountNumber);
}
