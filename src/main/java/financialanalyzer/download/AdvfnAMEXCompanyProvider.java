/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import financialanalyzer.config.AppConfig;
import financialanalyzer.objects.Company;
import financialanalyzer.objects.StockHistory;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class AdvfnAMEXCompanyProvider extends AbstractCompanyProvider implements CompanyProvider {

    private static final Logger LOGGER = Logger.getLogger(AdvfnAMEXCompanyProvider.class.getName());

    private static String download_url = "https://old.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=amex&render=download";
    //private static String latest_filename = AppConfig.companyDownloadDir + "/amex_latest.csv";

    @Override
    public List<Company> getAllCompanies() {
        LOGGER.info("Starting getAllCompanies");
        this.downloadCSVForExchangeFromNasDaq(download_url, this.getLatestFileName());
        List<Company> companies = this.processCSVForExchangedFromNasDaq(this.getLatestFileName(), "amex");

        LOGGER.info("Ending getAllCompanies");
        return companies;
    }

    @Override
    public List<Company> getCompaniesBeginningWithLetter(String _letter) {
        LOGGER.info("Starting getCompaniesBeginningWithLetter");
        LOGGER.info("Ending getCompaniesBeginningWithLetter");
        return null;
    }
    private String getLatestFileName() {
        return this.appConfig.getCompanyDownloadDir() + "/amex_latest.csv";
    }
    
    @Override
    public List<StockHistory> getStockHistoryForCompany(String _symbol) {
        return this.getStockHistoryForCompanyForDay(_symbol,null);
    }

    @Override
    public List<StockHistory> getStockHistoryForCompanyForDay(String _symbol, Date _date) {
        return this.downloadAndProcessCSVFromNasDaq(CompanyProvider.EXCHANGE_AMEX,_symbol, _date);
        //return this.downloadTimeHistoryAlphavantage(CompanyProvider.EXCHANGE_AMEX,_symbol, _date);
    }

}
