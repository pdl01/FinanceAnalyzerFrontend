/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.StockHistory;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pldor
 */
public interface CompanyProvider {
    List<Company> getAllCompanies();
    List<Company> getCompaniesBeginningWithLetter(String _letter);
    List<StockHistory> getStockHistoryForCompany(String _symbol);
    List<StockHistory> getStockHistoryForCompanyForDay(String _symbol,Date _date);
}
