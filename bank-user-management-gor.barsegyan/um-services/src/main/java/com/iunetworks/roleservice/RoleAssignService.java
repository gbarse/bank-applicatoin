package com.iunetworks.roleservice;


import com.iunetworks.BankUserRepository;
import com.iunetworks.CustomerUserRepository;
import com.iunetworks.RoleRepository;
import com.iunetworks.dtos.roledtos.RoleAssignDto;
import com.iunetworks.entities.BankUser;
import com.iunetworks.entities.CustomerUser;
import com.iunetworks.entities.Role;
import com.iunetworks.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleAssignService implements RoleAssignServiceImpl {

  private final BankUserRepository bankUserRepository;
  private final CustomerUserRepository customerUserRepository;
  private final RoleRepository roleRepository;

  @Autowired
  public RoleAssignService(BankUserRepository bankUserRepository, CustomerUserRepository customerUserRepository, RoleRepository roleRepository) {
    this.bankUserRepository = bankUserRepository;
    this.customerUserRepository = customerUserRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public String roleAssignToBankUser(RoleAssignDto roleAssignDto){
    BankUser bankUser=bankUserRepository.findByIdAndDeletedIsNull(roleAssignDto.getUserId()).
      orElseThrow(()->new ResourceNotFoundException("There is no  bank user with such id."));
    Set<Role> roles=  roleRepository.findAllByIdInAndDeletedIsNull(roleAssignDto.getRoleId());

    bankUser.addRoles(roles);

    bankUserRepository.save(bankUser);

    return "Role assigned to bank user.";
  }



  @Override
  public String roleAssignToCustomer(RoleAssignDto roleAssignDto) {
    CustomerUser customerUser = customerUserRepository.findByIdAndDeletedIsNull(roleAssignDto.getUserId()).
      orElseThrow(() -> new ResourceNotFoundException("There is no  customer with such id."));
    Set<Role> roles = roleRepository.findAllByIdInAndDeletedIsNull(roleAssignDto.getRoleId());
    customerUser.addRole(roles);
    customerUserRepository.save(customerUser);
    return "Role assigned to customer.";
  }

  public String removeRoleFromBankUser(RoleAssignDto roleAssignDto){
    BankUser bankUser=bankUserRepository.findByIdAndDeletedIsNull(roleAssignDto.getUserId()).
      orElseThrow(()->new ResourceNotFoundException("There is no  bank user with  id = ." + roleAssignDto.getUserId()));
    Set<Role> roles=  roleRepository.findAllByIdInAndDeletedIsNull(roleAssignDto.getRoleId());
    bankUser.removeRoles(roles);
    bankUserRepository.save(bankUser);

    return "Role assigned to bank user.";
  }

  public String removeRoleFromCustomerUser (RoleAssignDto roleAssignDto){
    CustomerUser customerUser=customerUserRepository.findByIdAndDeletedIsNull(roleAssignDto.getUserId()).
      orElseThrow(()->new ResourceNotFoundException("There is no  bank user with id = " + roleAssignDto.getUserId()));
    Set<Role> roles=  roleRepository.findAllByIdInAndDeletedIsNull(roleAssignDto.getRoleId());
    customerUser.removeRoles(roles);
    customerUserRepository.save(customerUser);

    return "Role assigned to bank user.";
  }

}
