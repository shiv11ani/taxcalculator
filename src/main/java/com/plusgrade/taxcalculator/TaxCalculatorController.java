package com.plusgrade.taxcalculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaxCalculatorController {

    private static final List<Object> RATES = new ArrayList<>();
    static {
    	RATES.add(2019);
    	RATES.add(2020);
    	RATES.add(2021);
    	RATES.add(2022);
    }
    
    private static final Map<Integer, Double> BANDS = new HashMap<>();
    static {
    	BANDS.put(0, 0.0);
    	BANDS.put(50197, 0.15);
    	BANDS.put(100392, 0.205);
    	BANDS.put(155625, 0.26);
    	BANDS.put(221708, 0.29);
    	//BANDS.put(1234567, 0.33);
    }

    @GetMapping("/tax-calculator/tax-year/{year}")
    public TaxResponse getTaxRatesByYear(@PathVariable int year) {
        if (RATES.contains(year)) {
        	 return new TaxResponse(year, BANDS);
        } else {
            throw new IllegalArgumentException("Tax rates for the specified year are not available.");
        }
    }

    @GetMapping("/tax-calculator/calculate")
    public ResultTaxCalculator calculateTax(
            @RequestParam("income") double income,
            @RequestParam("year") int year) {

        if (!RATES.contains(year)) {
            throw new IllegalArgumentException("Tax rates for the specified year are not available.");
        }

        double totalTaxes = calculateTotalTaxes(income);

        return new ResultTaxCalculator(income, year, totalTaxes, BANDS);
        
    }
    
    private double calculateTotalTaxes(double income) {
        double totalTaxes = 0.0;
        double previousBand = 0.0;

        for (Map.Entry<Integer, Double> entry : BANDS.entrySet()) {
            int upperLimit = entry.getKey();
            double taxRate = entry.getValue();

            if (income <= upperLimit) {
                //totalTaxes += (income - previousBand) * taxRate;
                break;
            } else {
                totalTaxes += (upperLimit - previousBand) * taxRate;
                previousBand = upperLimit;
            }
        }

        return totalTaxes;
        
    }

}
