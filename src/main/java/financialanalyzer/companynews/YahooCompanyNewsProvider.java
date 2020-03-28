
package financialanalyzer.companynews;

import financialanalyzer.http.HttpFetcher;
import financialanalyzer.objects.Company;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class YahooCompanyNewsProvider implements CompanyNewsProvider {

    private static final Logger LOGGER = Logger.getLogger(YahooCompanyNewsProvider.class.getName());
    public static final String PROVIDER_IDENTIFIER = "yahoo";

    @Autowired
    protected HttpFetcher httpFetcher;

    @Override
    public List<CompanyNewsItem> getCompanyNewsItems(Company _company, int _numberOfArticles) {
        LOGGER.info(YahooCompanyNewsProvider.PROVIDER_IDENTIFIER+":beginning getCompanyNewsItems");
        List<CompanyNewsItem> cnis = new ArrayList<>();
        return cnis;
    }

    @Override
    public String getIdentifier() {
        return YahooCompanyNewsProvider.PROVIDER_IDENTIFIER;
    }
    
}
