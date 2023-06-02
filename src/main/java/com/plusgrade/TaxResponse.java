package com.plusgrade.taxcalculator;

import java.util.Map;

public class TaxResponse {
	private int year;
    private Map<Integer, Double> taxBands;

    public TaxResponse(int year, Map<Integer, Double> taxBands) {
        this.year = year;
        this.taxBands = taxBands;
    }

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Map<Integer, Double> getTaxBands() {
		return taxBands;
	}

	public void setTaxBands(Map<Integer, Double> taxBands) {
		this.taxBands = taxBands;
	}
}

