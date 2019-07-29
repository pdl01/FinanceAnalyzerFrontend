
package financialanalyzer.download;

import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class AllStockNamesDownloadDriver {
    private static final Logger LOGGER = Logger.getLogger(AllStockNamesDownloadDriver.class.getName());
    
    @Scheduled(fixedRate = 5000)
    public void fetchLatestData() {
        
    }
    
    
    public void retrieveAMEXCompanies(){
        
    }
}
