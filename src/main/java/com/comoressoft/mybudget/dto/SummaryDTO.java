package com.comoressoft.mybudget.dto;

public class SummaryDTO {
	private int monthPosition;
	private TotalSummaryDTO totalSummary;

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
