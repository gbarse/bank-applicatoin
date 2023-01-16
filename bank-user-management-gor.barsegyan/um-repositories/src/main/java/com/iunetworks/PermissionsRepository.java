package com.iunetworks;

import com.iunetworks.entities.Permission;
import com.iunetworks.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PermissionsRepository extends JpaRepository<Permission, UUID> {
  Set<Permission> findAllByNameInAndDeletedIsNull(Set<String> names);

  Set<Permission> findAllByIdInAndDeletedIsNull(Set<UUID> privelegesIdList);
  Optional<Permission> findByIdAndDeletedIsNull(UUID id);
}
