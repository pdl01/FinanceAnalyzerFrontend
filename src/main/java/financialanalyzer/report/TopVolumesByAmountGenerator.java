/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.report;

import financialanalyzer.respository.StockHistoryReportSearchRepo;
import org.elasticsearch.action.search.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pldor
 */
public class TopVolumesByAmountGenerator implements ReportGenerator{
    
    @Autowired
    private StockHistoryReportSearchRepo  stockHistoryReportSearchRepo;
    
    @Override
    public ReportSummary getReport(String _startDate, String _endDate) {
        SearchRequest searchRequest = new SearchRequest(StockHistoryReportSearchRepo.STOCK_HISTORY_INDEX);
        
        this.stockHistoryReportSearchRepo.searchForReport(searchRequest);
        ReportSummary summary = null;
        return summary;
    }
    
}
