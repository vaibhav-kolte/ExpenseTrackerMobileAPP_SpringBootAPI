package com.mycode.myExpenseTracker.entities;

import java.math.BigDecimal;

public interface ExpenseSummary {
    BigDecimal getTotalDebit();
    BigDecimal getTotalCredit();
    String getMonthYear();
}
