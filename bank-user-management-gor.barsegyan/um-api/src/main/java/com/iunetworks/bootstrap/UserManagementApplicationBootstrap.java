package com.iunetworks.bootstrap;

import com.iunetworks.PermissionsRepository;
import com.iunetworks.RoleRepository;
import com.iunetworks.entities.Permission;
import com.iunetworks.entities.Role;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserManagementApplicationBootstrap {

  private static final String admin_role = "ADMIN";
  private static final String customer_role = "CUSTOMER";
  private static final String super_admin_role = "SUPERADMIN";


  private final RoleRepository repository;
  private final PermissionsRepository permissionsRepository;

  public UserManagementApplicationBootstrap(RoleRepository repository, PermissionsRepository permissionsRepository) {
    this.repository = repository;
    this.permissionsRepository = permissionsRepository;
  }


  public void init() {

    if (!this.repository.existsByNameAndDeletedIsNull(admin_role)) {
      Role adminRole = new Role();
      adminRole.setName(admin_role);
      adminRole.setPermission(this.adminPermissions());
      this.repository.save(adminRole);
    }
    if (!this.repository.existsByNameAndDeletedIsNull(customer_role)) {
      Role customerRole = new Role();
      customerRole.setName(customer_role);

      customerRole.setPermission(this.customerPermissions());
      this.repository.save(customerRole);

    }
    if (!this.repository.existsByNameAndDeletedIsNull(super_admin_role)) {
      Role superAdminRole = new Role();
      superAdminRole.setName(super_admin_role);
      superAdminRole.setPermission(this.adminPermissions());
      this.repository.save(superAdminRole);
    }



  }




  private Set<Permission> adminPermissions() {
    return this.permissionsRepository.findAllByNameInAndDeletedIsNull(
      Set.of("can_view_all_customer_B", "can_create_admin_B", "can_delete_admin_B", "can_view_all_admin_B",
        "can_update_admin_B", "can_recover_admin_B", "can_view_all_admin_by_id_B", "can_recover_customer_B",
        "can_view_all_customer_by_id_B", "can_create_roles_B", "can_delete_roles_B", "an_view_roles_B",
        "can_update_roles_B", "can_delete_customer_B", "can_recover_roles_B", "can_assign_role_B", "can_assign_permission_B")
    );
  }

  private Set<Permission> customerPermissions() {
    return this.permissionsRepository.findAllByNameInAndDeletedIsNull(
      Set.of("can_create_customer_C", "can_update_customer_C")
    );
  }

//  private Set<Permission> superadminPermissions() {
//    return this.permissionsRepository.findAllByNameInAndDeletedIsNull(
//      Set.of("can_view_all_customer_B", "can_create_admin_B", "can_delete_admin_B", "can_view_all_admin_B",
//        "can_update_admin_B", "can_recover_admin_B", "can_view_all_admin_by_id_B", "can_recover_customer_B",
//        "can_view_all_customer_by_id_B", "can_create_roles_B", "can_delete_roles_B", "an_view_roles_B",
//        "can_update_roles_B", "can_delete_customer_B", "can_recover_roles_B", "can_assign_role_B", "can_assign_permission_B")
//    );
//  }

}

