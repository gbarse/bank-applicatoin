package com.iunetworks;

import com.iunetworks.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByName(String name);
  boolean existsByIdAndDeletedIsNull(UUID id);
  boolean existsById (UUID id);
  Optional<Role> findByIdAndDeletedIsNull(UUID id);
  default Optional<Role> findAdminRole() {
    return findByNameAndDeletedIsNull("ADMIN");
  }
  default Optional<Role> findCustomerRole() {
    return findByNameAndDeletedIsNull("CUSTOMER");
  }
//  default Optional<Role> findSuperAdminRole() {
//    return findByNameAndDeletedIsNull("SUPERADMIN");
//  }
//  default Optional<Role> findVipCustomerRole() {
//    return findByNameAndDeletedIsNull("VIPCUSTOMER");
//  }
  boolean existsByNameAndDeletedIsNull(String name);
  Optional<Role> findByNameAndDeletedIsNull(String name);

    Set<Role> findAllByIdInAndDeletedIsNull(Collection<UUID> id);

  default Optional<Role> findSuperAdminRole() {
    return findByNameAndDeletedIsNull("SUPERADMIN");
  }

}
