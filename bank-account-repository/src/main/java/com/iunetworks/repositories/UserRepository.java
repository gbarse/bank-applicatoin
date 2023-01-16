package com.iunetworks.repositories;

import com.iunetworks.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


  boolean existsUserByLoggedIdAndDeletedIsNull(UUID loggedId);


  Optional<User> findByIdAndDeletedIsNull(UUID uuid);


  List<User> findAllByDeletedIsNull();

  boolean existsByIdAndDeletedIsNull(UUID id);
}
