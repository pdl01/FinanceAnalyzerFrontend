/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.stockpriceprediction;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.CompanySearchProperties;
import java.util.List;

/**
 *
 * @author pldor
 */
public interface FutureStockPriceRepo {
    public FutureStockPrice submit(FutureStockPrice _item);
    public boolean delete(FutureStockPrice _item);
    public List<FutureStockPrice> searchForFutureStockPrice(FutureStockPriceSearchProperties _csp);
}
