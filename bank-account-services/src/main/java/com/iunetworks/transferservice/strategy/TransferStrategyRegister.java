package com.iunetworks.transferservice.strategy;

import com.iunetworks.transferservice.model.AmountTransferType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class TransferStrategyRegister {

  private final Map<AmountTransferType, TransferStrategy> strategy;

  public TransferStrategyRegister(final List<TransferStrategy> strategies) {
    this.strategy = new EnumMap<>(AmountTransferType.class);
    strategies.forEach(str -> strategy.put(str.byType(), str));
  }

  public TransferStrategy byStrategy(final AmountTransferType transferType) {
    final TransferStrategy foundedStrategy = strategy.get(transferType);
    if (foundedStrategy == null) {
      throw new RuntimeException("Strategy not found by type :" + transferType);
    }
    return foundedStrategy;
  }
}

