package com.iunetworks.transferservice.strategy.impl;

import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.rateservice.TransactionAmountService;
import com.iunetworks.repositories.LegalAccountRepository;
import com.iunetworks.repositories.PhysicalAccountRepository;
import com.iunetworks.transferservice.model.AmountTransaction;
import com.iunetworks.transferservice.model.AmountTransferType;
import com.iunetworks.transferservice.strategy.TransferStrategy;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component

public class PhysicalAccountToLegalAccountStrategy implements TransferStrategy {

  private final TransactionAmountService rateService;

  private final PhysicalAccountRepository physicalAccountRepository;

  private final LegalAccountRepository legalAccountRepository;

  public PhysicalAccountToLegalAccountStrategy(final TransactionAmountService rateService, PhysicalAccountRepository physicalAccountRepository, LegalAccountRepository legalAccountRepository) {
    this.physicalAccountRepository = physicalAccountRepository;
    this.rateService = rateService;
    this.legalAccountRepository = legalAccountRepository;
  }

  @Override
  @Transactional
  public void transfer(final AmountTransaction amountTransaction) {

    LegalAccount to = legalAccountRepository.findByAccountNumberAndDeletedIsNull(amountTransaction.getTo())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", amountTransaction.getTo())));

    PhysicalAccount from = physicalAccountRepository.findByAccountNumberAndDeletedIsNull(amountTransaction.getFrom())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", amountTransaction.getFrom())));

    BigDecimal finalAmount = getTransferAmount(amountTransaction.getAmount(),  to.getCurrency(), from.getCurrency());
    if (from.getAmount().compareTo(amountTransaction.getAmount()) >= 0) {
      from.setAmount(from.getAmount().subtract(amountTransaction.getAmount()));
      to.setAmount(to.getAmount().add(finalAmount));
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
    legalAccountRepository.save(to);
    physicalAccountRepository.save(from);
  }

  @Override
  public AmountTransferType byType() {
    return AmountTransferType.PHYSICAL_ACCOUNT_TO_LEGAL_ACCOUNT;
  }

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransactionAmount(out, in, amount);
  }
}
