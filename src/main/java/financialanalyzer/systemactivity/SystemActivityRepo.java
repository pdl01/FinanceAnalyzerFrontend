/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.systemactivity;

import java.util.List;

/**
 *
 * @author pldor
 */
public interface SystemActivityRepo {
    public SystemActivity submit(SystemActivity _item);
    public boolean delete(SystemActivity _item);
    public List<SystemActivity> searchForSystemActivity(SystemActivitySearchProperties _sp);
}
