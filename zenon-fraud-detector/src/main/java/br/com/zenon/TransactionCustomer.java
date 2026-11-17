package br.com.zenon;

import java.math.BigDecimal;

public record TransactionCustomer (
     String nameOrig,
     BigDecimal oldBalanceOrg,
     BigDecimal newBalanceOrg
){}
