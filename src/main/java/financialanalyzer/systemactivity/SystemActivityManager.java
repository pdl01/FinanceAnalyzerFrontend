/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.systemactivity;

/**
 *
 * @author pldor
 */
public interface SystemActivityManager {
    public final static String ACTIVITY_TYPE_STOCK_SEARCH = "stock-search";
    public final static String ACTIVITY_TYPE_STOCK_HISTORY_DOWNLOAD = "stock-history-download";
    public final static String ACTIVITY_TYPE_COMPANY_NEWS = "company-news";
    
    void saveSystemActivity(String _symbol, String _exchange, String _type, String _message);
    
}
