/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.systemactivity;

import financialanalyzer.elasticsearch.ElasticSearchManager;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class SystemActivitySearchRepo extends ElasticSearchManager implements SystemActivityRepo {

    @Override
    public SystemActivity submit(SystemActivity _item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(SystemActivity _item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SystemActivity> searchForSystemActivity(SystemActivitySearchProperties _sp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
