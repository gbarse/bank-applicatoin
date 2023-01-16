package com.iunetworks.entities;

import com.iunetworks.enums.CompanyType;
import com.iunetworks.enums.Currency;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "t_legal_account")
public class LegalAccount extends Account {

  @Column(name = "companyName", nullable = false)
  private String companyName;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "companyType", nullable = false)
  private CompanyType companyType;

  @Column(name = "ssh", nullable = false)
  private String ssh;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public CompanyType getCompanyType() {
    return companyType;
  }

  public void setCompanyType(CompanyType companyType) {
    this.companyType = companyType;
  }

  public LegalAccount() {
  }

  public String getSsh() {
    return ssh;
  }

  public void setSsh(String ssh) {
    this.ssh = ssh;
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
