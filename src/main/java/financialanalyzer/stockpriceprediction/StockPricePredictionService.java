/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.stockpriceprediction;

import financialanalyzer.objects.Company;

/**
 *
 * @author pldor
 */
public interface StockPricePredictionService {
    public FutureStockPrice getFutureStockPrice(Company _company,int _futureDays);
    
}
