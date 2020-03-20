/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.StockHistory;
import financialanalyzer.respository.StockHistorySearchRepo;
import financialanalyzer.systemactivity.SystemActivityManager;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class StockHistoryDowloadServiceImpl implements StockHistoryDownloadService {

    @Autowired
    private CompanyProvider advfnAMEXCompanyProvider;

    @Autowired
    private CompanyProvider advfnNYSECompanyProvider;

    @Autowired
    private CompanyProvider advfnNasDaqCompanyProvider;

    @Autowired
    private StockHistorySearchRepo stockHistorySearchRepoImpl;

    @Autowired
    private SystemActivityManager systemActivityManagerImpl;
    
    @Override
    public List<StockHistory> fetchDataForCompany(Company company) {
        return this.fetchDataForCompany(company, null);
    }

    @Override
    public List<StockHistory> fetchDataForCompany(Company company, Date _date) {
        if (company == null) {
            return null;
        }
        List<StockHistory> shs = null;
        if (_date != null) {
            if (company.getStockExchange().equalsIgnoreCase(CompanyProvider.EXCHANGE_NASDAQ)) {
                shs = this.advfnNasDaqCompanyProvider.getStockHistoryForCompany(company.getStockSymbol());
            } else if (company.getStockExchange().equalsIgnoreCase(CompanyProvider.EXCHANGE_NYSE)) {
                shs = this.advfnNYSECompanyProvider.getStockHistoryForCompany(company.getStockSymbol());
            } else if (company.getStockExchange().equalsIgnoreCase(CompanyProvider.EXCHANGE_AMEX)) {
                shs = this.advfnAMEXCompanyProvider.getStockHistoryForCompany(company.getStockSymbol());
            }

        } else {
            if (company.getStockExchange().equalsIgnoreCase(CompanyProvider.EXCHANGE_NASDAQ)) {
                shs = this.advfnNasDaqCompanyProvider.getStockHistoryForCompanyForDay(company.getStockSymbol(),_date);
            } else if (company.getStockExchange().equalsIgnoreCase(CompanyProvider.EXCHANGE_NYSE)) {
                shs = this.advfnNYSECompanyProvider.getStockHistoryForCompanyForDay(company.getStockSymbol(),_date);
            } else if (company.getStockExchange().equalsIgnoreCase(CompanyProvider.EXCHANGE_AMEX)) {
                shs = this.advfnAMEXCompanyProvider.getStockHistoryForCompanyForDay(company.getStockSymbol(),_date);
            }
        }
        if (shs != null) {
            for (StockHistory item : shs) {
                this.stockHistorySearchRepoImpl.submit(item);
            }
            this.systemActivityManagerImpl.saveSystemActivity(company.getStockSymbol(), company.getStockExchange(), SystemActivityManager.ACTIVITY_TYPE_STOCK_HISTORY_DOWNLOAD, "Updated items:"+shs.size());
            /*
            shs.forEach(item -> {
                this.stockHistorySearchRepoImpl.submit(item);

            });
             */
        }
        return shs;
    }

}
