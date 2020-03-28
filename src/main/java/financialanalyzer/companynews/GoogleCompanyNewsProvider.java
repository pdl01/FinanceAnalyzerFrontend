/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.companynews;

import financialanalyzer.http.HTMLPage;
import financialanalyzer.http.HttpFetcher;
import financialanalyzer.objects.Company;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class GoogleCompanyNewsProvider implements CompanyNewsProvider {

    private static final Logger LOGGER = Logger.getLogger(GoogleCompanyNewsProvider.class.getName());
    public static final String PROVIDER_IDENTIFIER = "google";
    @Autowired
    protected HttpFetcher httpFetcher;

    @Override
    public List<CompanyNewsItem> getCompanyNewsItems(Company _company, int _numberOfArticles) {
        LOGGER.info(GoogleCompanyNewsProvider.PROVIDER_IDENTIFIER + ":beginning getCompanyNewsItems");
        List<CompanyNewsItem> cnis = new ArrayList<>();

        HTMLPage companyNewsIndexPage = null;
        String url = "";
        String resolvedurl = url;
        try {
            LOGGER.info("Starting Download of company News for :" + _company.getStockSymbol());
            companyNewsIndexPage = this.httpFetcher.getResponse(resolvedurl, true);

            LOGGER.info("Completed Download of company News index for :" + _company.getStockSymbol());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception fetching company news for:" + _company.getStockSymbol(), ex);

        }

        return cnis;
    }

    @Override
    public String getIdentifier() {
        return GoogleCompanyNewsProvider.PROVIDER_IDENTIFIER;
    }

}
