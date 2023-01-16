package com.iunetworks.repositories;

import com.iunetworks.entities.LegalCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface LegalCreditCardRepository extends JpaRepository<LegalCreditCard, UUID> {

  boolean existsByLegalAccountIdAndDeletedIsNull(UUID id);

  Optional<LegalCreditCard> findByIdAndDeletedIsNull(UUID uuid);

  List<LegalCreditCard> findAllByDeletedIsNull();

  boolean existsByCardNumberAndDeletedIsNull(String cardNumber);

  Optional<LegalCreditCard> findByCardNumberAndDeletedIsNull(String toCreditNumber);

  @Query("SELECT b FROM LegalCreditCard b WHERE b.status = 'IN_PROCESS'")
  List<LegalCreditCard> getAllByInProcessLeCards();
}
