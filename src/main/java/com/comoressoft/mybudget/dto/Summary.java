package com.comoressoft.mybudget.dto;

public class Summary {
	private int monthPosition;
	private TotalSummary totalSummary;

	public int getMonthPosition() {
		return monthPosition;
	}

	public void setMonthPosition(int monthPosition) {
		this.monthPosition = monthPosition;
	}

	public TotalSummary getTotalSummary() {
		return totalSummary;
	}

	public void setTotalSummary(TotalSummary totalSummary) {
		this.totalSummary = totalSummary;
	}

}
