/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.respository;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.CompanySearchProperties;
import financialanalyzer.objects.StockHistory;
import financialanalyzer.objects.StockHistorySearchProperties;
import java.util.List;

/**
 *
 * @author pldor
 */
public interface StockHistoryRepo {

    public StockHistory submit(StockHistory _item);

    public boolean delete(StockHistory _item);

    public List<StockHistory> searchForStockHistory(StockHistorySearchProperties _shsp);
}
