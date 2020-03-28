/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.stockpriceprediction;

import financialanalyzer.elasticsearch.ElasticSearchManager;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class FutureStockPriceSearchRepo extends ElasticSearchManager implements FutureStockPriceRepo{

    @Override
    public FutureStockPrice submit(FutureStockPrice _item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(FutureStockPrice _item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FutureStockPrice> searchForFutureStockPrice(FutureStockPriceSearchProperties _csp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
