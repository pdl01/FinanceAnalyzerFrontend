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
public class CompanyNewsServiceImpl implements CompanyNewsService {

    private static final Logger LOGGER = Logger.getLogger(CompanyNewsServiceImpl.class.getName());
    


    
    @Autowired
    private CompanyNewsProviderRegistry companyNewProviderRegistry;
    
    @Override
    public List<CompanyNewsItem> getCompanyNewsItems(Company _company, int _numberOfArticlesPerProvider) {

        List<CompanyNewsItem> cnis = new ArrayList<>();
            
        for (CompanyNewsProvider provider: this.companyNewProviderRegistry.getProviders()) {
            List<CompanyNewsItem> cnis_items = provider.getCompanyNewsItems(_company, _numberOfArticlesPerProvider);
            if (cnis_items != null) {
                cnis.addAll(cnis_items);
            }
        }
        
        return cnis;
    }

}
