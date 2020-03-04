
package financialanalyzer.download;

import financialanalyzer.objects.Company;
import financialanalyzer.respository.CompanyRepo;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class AllStockNamesDownloadDriver {
    private static final Logger LOGGER = Logger.getLogger(AllStockNamesDownloadDriver.class.getName());
    
    @Autowired
    private CompanyRepo companySearchRepo;
    
    @Autowired
    private CompanyProvider advfnAMEXCompanyProvider;
    
    @Autowired
    private CompanyProvider advfnNYSECompanyProvider;
        
    @Autowired
    private CompanyProvider advfnNasDaqCompanyProvider;    
    
    //@Scheduled(initialDelay = 5000,fixedRate = 1000*60*60*24)
    @Scheduled(initialDelay=1000*60*60*24,fixedRate = 1000*60*60*24)
    public void fetchLatestData() {
        LOGGER.info("Starting fetchLatestData");
        List<Company> companyList = this.advfnAMEXCompanyProvider.getAllCompanies();
        this.processCompanyList(companyList);
        
        companyList = this.advfnNYSECompanyProvider.getAllCompanies();
        this.processCompanyList(companyList);
        
        companyList = this.advfnNasDaqCompanyProvider.getAllCompanies();
        this.processCompanyList(companyList);
        
        LOGGER.info("Ending fetchLatestData");
    }
    
    public void processCompanyList(List<Company> _companies) {
        if (_companies == null) {
            return;
        }
	//Output : C
        int companyCounter = 0;
	_companies.forEach(item->{
            LOGGER.info("Submitting:" + item.getStockExchange() + item.getName() + ":" + item.getStockSymbol());
            this.companySearchRepo.submit(item);
   
	});
    }
    
    public void retrieveAMEXCompanies(){
        
    }
}
