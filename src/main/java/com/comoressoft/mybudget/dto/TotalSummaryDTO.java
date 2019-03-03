package com.comoressoft.mybudget.dto;

import java.math.BigDecimal;

public class TotalSummaryDTO {

	private BigDecimal income;
	private BigDecimal expense;
	private BigDecimal balance;
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public BigDecimal getExpense() {
		return expense;
	}
	public void setExpense(BigDecimal expense) {
		this.expense = expense;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	

}
