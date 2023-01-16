package com.iunetworks.repositories;

import com.iunetworks.entities.PhysicalCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhCreditCardRepository extends JpaRepository<PhysicalCreditCard, UUID> {

  boolean existsByPhysicalAccountIdAndDeletedIsNull(UUID id);


  Optional<PhysicalCreditCard> findByIdAndDeletedIsNull(UUID uuid);

  List<PhysicalCreditCard> findAllByDeletedIsNull();

  boolean existsByIdAndDeletedIsNull(UUID id);

  boolean existsByCardNumberAndDeletedIsNull(String toCardNumber);

  Optional<PhysicalCreditCard> findByCardNumberAndDeletedIsNull(String toCardNumber);


  @Query("SELECT b FROM PhysicalCreditCard b WHERE b.status = 'IN_PROCESS'")
  List<PhysicalCreditCard> getAllByInProcessPhCards();
}
