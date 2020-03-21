/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import financialanalyzer.config.ActiveMQConfig;
import financialanalyzer.objects.Company;
import financialanalyzer.objects.CompanySearchProperties;
import financialanalyzer.objects.StockHistory;
import financialanalyzer.respository.CompanyRepo;
import financialanalyzer.respository.CompanySearchRepo;
import financialanalyzer.respository.StockHistorySearchRepo;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class StockHistoryDownloadDriver {

    private static final Logger LOGGER = Logger.getLogger(StockHistoryDownloadDriver.class.getName());
    @Autowired
    private StockHistorySearchRepo stockHistorySearchRepo;

    @Autowired
    private CompanySearchRepo companySearchRepo;

    @Autowired
    StockHistoryDownloadService stockHistoryDownloadServiceImpl;

    //@Autowired
    //private JmsTemplate jmsTemplate;

    //@Scheduled(initialDelay = 5000, fixedRate = 1000 * 60 * 60 * 24)
    //@Scheduled(initialDelay = 1000 * 60 * 60 * 24, fixedRate = 1000 * 60 * 60 * 24)
    public void fetchLatestData() {
        this.fetchLatestData(null);
    }
    @Scheduled(cron = "0 15 1 * * ?")
    public void fetchDaily() {
        LOGGER.info("Starting fetchDaily");

        Date todaysDate = new Date();
        this.fetchLatestData(todaysDate);
        LOGGER.info("Completed fetchDaily");

    }

    public void fetchLatestData(Date _date) {
        LOGGER.info("Starting fetchLatestData");

        String[] exchangeArray = {CompanyProvider.EXCHANGE_AMEX, CompanyProvider.EXCHANGE_NASDAQ, CompanyProvider.EXCHANGE_NYSE};

        for (String exchange : exchangeArray) {
            CompanySearchProperties csp = new CompanySearchProperties();
            csp.setStockExchange(exchange);
            int numResultsPerBatch = 200;
            boolean hasMoreResults = true;
            csp.setStartResults(0);
            csp.setNumResults(numResultsPerBatch);

            while (hasMoreResults) {
                List<Company> companies = this.companySearchRepo.searchForCompany(csp);
                if (companies != null) {
                    LOGGER.info("Processing :" + companies.size() + " companies");
                } else {
                    LOGGER.info("No companies returned");
                }
                if (companies != null) {
                    for (Company item : companies) {

                        LOGGER.info("Submitting:" + item.getStockExchange() + item.getName() + ":" + item.getStockSymbol());
                        //this.companySearchRepo.submit(item);
                        List<StockHistory> shs = null;
                        StockHistoryDownloadTask shdt = new StockHistoryDownloadTask();
                        shdt.setSymbol(item.getStockSymbol());
                        shdt.setExchange(item.getStockExchange());

                        if (_date == null) {
                            shdt.setDownloadAllAvailalble(true);
                            //shs = this.stockHistoryDownloadServiceImpl.fetchDataForCompany(item);
                            //shs = this.advfnNasDaqCompanyProvider.getStockHistoryForCompany(item.getStockSymbol());
                        } else {
                            shdt.setDownloadAllAvailalble(false);
                            shdt.setRetrieveDate(_date);
                            //shs = this.stockHistoryDownloadServiceImpl.fetchDataForCompany(item, _date);
                        }
                        this.stockHistoryDownloadServiceImpl.queueStockHistoryDownloadTask(shdt);
                        //this.jmsTemplate.convertAndSend(ActiveMQConfig.STOCK_HISTORY_DOWNLOAD_QUEUE, shdt);
                        /*
                        if (shs != null) {
                            LOGGER.info("Submitting stock history data for :" + item.getStockSymbol());
                            for (StockHistory shs_item: shs) {
                                this.stockHistorySearchRepo.submit(shs_item);
                            }
                            
                            LOGGER.info("Completed submitting stock history data for :" + item.getStockSymbol());

                        }
                         */

                    }
                }
                if (companies != null && companies.size() == numResultsPerBatch) {
                    hasMoreResults = true;
                    csp.setStartResults(csp.getStartResults() + numResultsPerBatch);
                } else {
                    hasMoreResults = false;
                }

            }
            //get all companies for exchange

        }

        //for each company
        //  get the 
        //List<Company> companyList = this.advfnAMEXCompanyProvider.getAllCompanies();
        //this.processCompanyList(companyList);
        //companyList = this.advfnNYSECompanyProvider.getAllCompanies();
        //this.processCompanyList(companyList);
        //companyList = this.advfnNasDaqCompanyProvider.getAllCompanies();
        //this.processCompanyList(companyList);
        LOGGER.info("Ending fetchLatestData");
    }

}
