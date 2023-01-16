package com.iunetworks.entities;

import javax.persistence.*;


@Entity
@Table(name = "t_physical_account")
public class PhysicalAccount extends Account  {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
   private User user;

  @Column(name = "ssn")
  private String ssn;

  public PhysicalAccount(User user) {
    this.user = user;
  }


  public PhysicalAccount() {

  }



  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

//  @Override
//  public BigDecimal amount() {
//    return this.amount;
//  }
//
//  @Override
//  public Currency currency() {
//    return this.currency;
//  }
//
//  @Override
//  public void addAmount(BigDecimal amount) {
//    this.amount = this.amount.add(amount);
//  }
}
