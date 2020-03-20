/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.companynews;

import financialanalyzer.config.ActiveMQConfig;
import financialanalyzer.controller.v1.CompanyRestController;
import financialanalyzer.download.CompanyProvider;
import financialanalyzer.download.StockHistoryDownloadTask;
import financialanalyzer.objects.Company;
import financialanalyzer.objects.CompanySearchProperties;
import financialanalyzer.objects.StockHistory;
import financialanalyzer.respository.CompanySearchRepo;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author pldor
 */
public class CompanyNewsDownloadDriver {

    private static final Logger LOGGER = Logger.getLogger(CompanyNewsDownloadDriver.class.getName());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private CompanySearchRepo companySearchRepo;

    @Scheduled(cron = "0 15 4 * * ?")
    public void fetchDaily() {
        LOGGER.info("Starting fetchDaily");

        Date todaysDate = new Date();
        this.fetchLatestData(todaysDate);
        LOGGER.info("Completed fetchDaily");
    }

    public void fetchLatestData(Date _date) {
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
                        this.jmsTemplate.convertAndSend(ActiveMQConfig.COMPANY_NEWS_QUEUE, item);
                    }
                }
                if (companies != null && companies.size() == numResultsPerBatch) {
                    hasMoreResults = true;
                    csp.setStartResults(csp.getStartResults() + numResultsPerBatch);
                } else {
                    hasMoreResults = false;
                }
            }
         
        }
        LOGGER.info("Ending fetchLatestData");
    }
}

