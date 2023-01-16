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
public class LegalAccountToPhysicalAccountStrategy implements TransferStrategy {

  private final TransactionAmountService rateService;

  private final LegalAccountRepository legalEntityAccountsRepository;

  private final PhysicalAccountRepository physicalAccountRepository;



  public LegalAccountToPhysicalAccountStrategy(final LegalAccountRepository legalEntityAccountsRepository,
                                               final TransactionAmountService rateService, PhysicalAccountRepository physicalAccountRepository) {
    this.legalEntityAccountsRepository = legalEntityAccountsRepository;
    this.rateService = rateService;
    this.physicalAccountRepository = physicalAccountRepository;
  }



  @Override
  @Transactional
  public void transfer(final AmountTransaction amountTransaction) {

    PhysicalAccount to = physicalAccountRepository.findByAccountNumberAndDeletedIsNull(amountTransaction.getTo())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", amountTransaction.getTo())));

    LegalAccount from = legalEntityAccountsRepository.findByAccountNumberAndDeletedIsNull(amountTransaction.getFrom())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", amountTransaction.getFrom())));

    BigDecimal finalAmount = getTransferAmount(amountTransaction.getAmount(),  to.getCurrency(), from.getCurrency());
    if (from.getAmount().compareTo(amountTransaction.getAmount()) >= 0) {
      from.setAmount(from.getAmount().subtract(amountTransaction.getAmount()));
      to.setAmount(to.getAmount().add(finalAmount));
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
    physicalAccountRepository.save(to);
    legalEntityAccountsRepository.save(from);
  }

  @Override
  public AmountTransferType byType() {
    return AmountTransferType.LEGAL_ACCOUNT_TO_PHYSICAL_ACCOUNT;
  }

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransactionAmount(out, in, amount);
  }
}
