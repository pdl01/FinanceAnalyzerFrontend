/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.StockHistory;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pldor
 */
public interface StockHistoryDownloadService {

    public void queueCompanyForFetch(Company company, Date date, boolean retrieveAll);

    public void queueStockHistoryDownloadTask(StockHistoryDownloadTask _item);

    public List<StockHistory> fetchDataForCompany(Company company);

    public List<StockHistory> fetchDataForCompany(Company company, Date _date);
}
