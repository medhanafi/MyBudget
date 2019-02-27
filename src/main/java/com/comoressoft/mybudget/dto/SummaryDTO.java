package com.comoressoft.mybudget.dto;

public class SummaryDTO {
	private String monthName;
	private int monthPosition;
	private TotalSummaryDTO totalSummary;

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public int getMonthPosition() {
		return monthPosition;
	}

	public void setMonthPosition(int monthPosition) {
		this.monthPosition = monthPosition;
	}

	public TotalSummaryDTO getTotalSummary() {
		return totalSummary;
	}

	public void setTotalSummary(TotalSummaryDTO totalSummary) {
		this.totalSummary = totalSummary;
	}

}
