package com.iunetworks;

import com.iunetworks.dtos.customeruserdtos.CustomerUserUpdateDto;
import com.iunetworks.entities.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CustomerUserRepository extends JpaRepository<CustomerUser, UUID> {
//  static void save(CustomerUserUpdateDto partialUpdate, String id) {
//  }
//  CustomerUser getByEmail(String email);
  Optional<CustomerUser> findByEmail(String email);
    boolean existsByIdAndDeletedIsNull(UUID id);

  Optional <CustomerUser> findByEmailAndDeletedIsNull(String email);

  List<CustomerUser> findAllByDeletedIsNull();

    Optional<CustomerUser> findByIdAndDeletedIsNull(UUID userId);

  boolean existsByEmailAndDeletedIsNull(String email);
}
