/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.controller.v1;

import financialanalyzer.objects.StockHistory;
import financialanalyzer.objects.StockHistorySearchProperties;
import financialanalyzer.report.ReportGenerator;
import financialanalyzer.report.ReportSummary;
import financialanalyzer.report.TopVolumesByAmountGenerator;
import financialanalyzer.respository.StockHistoryRepo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pldor
 */
@RestController
@RequestMapping("/api/v1/reports/stocks")
public class StockSummaryReportsController {

    private static final Logger logger = Logger.getLogger(StockSummaryReportsController.class.getName());

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final static String TOP_GAINERS_BY_AMOUNT = "top-gainers-by-amount";
    private final static String TOP_GAINERS_BY_PERCENTAGE = "top-gainers-by-percentage";
    private final static String TOP_LOSERS_BY_AMOUNT = "top-losers-by-amount";
    private final static String TOP_LOSERS_BY_PERCENTAGE = "top-losers-by-percentage";
    private final static String TOP_VOLUMES_BY_AMOUNT = "top-volumes-by-amount";
    private final static String TOP_VOLUMES_BY_AVERAGE = "top-volumes-by-average";

    @Autowired
    private StockHistoryRepo stockHistorySearchRepo;

    @RequestMapping(value = "/report/{name}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse getReportForName(@PathVariable("name") String _name) {
        String dateString = sdf.format(new Date());
        return this.getReportForNameEndingOnDate(_name, dateString);
    }

    @RequestMapping(value = "/report/{name}/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse getReportForNameEndingOnDate(@PathVariable("name") String _name, @PathVariable("endDate") String _endDate) {

        return this.getReportForNameEndingOnDateForNumberOfDays(_name, _endDate, "30");
    }

    @RequestMapping(value = "/report/{name}/{endDate}/{numberOfDays}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse getReportForNameEndingOnDateForNumberOfDays(@PathVariable("name") String _name, @PathVariable("endDate") String _endDate, @PathVariable("numberOfDays") String _numberOfDays) {
        RestResponse response = new RestResponse();
        ReportSummary report = this.getReport(_name, _endDate, Integer.parseInt(_numberOfDays));
        response.setObject(report);
        return response;

    }

    private ReportSummary getReport(String _name, String _endDate, int _numOfDays) {
        ReportGenerator reportGenerator = this.getReportGenerator(_name);
        if (reportGenerator != null) {
            ReportSummary reportSummary = reportGenerator.getReport(this.getStartDate(_endDate, _numOfDays), _endDate);
            return reportSummary;
        }
        return null;
    }

    private ReportGenerator getReportGenerator(String _name) {
        return new TopVolumesByAmountGenerator();
    }

    private ReportSummary getTopVolumesByAmount(String _endDate, int _numOfDays) {
        ReportSummary report = new ReportSummary();
        report.setName("Top Volumes By Amount");
        report.setEndDate(_endDate);
        report.setStartDate(this.getStartDate(_endDate, _numOfDays));
        return report;
    }

    private ReportSummary getTopVolumesByAverage(String _endDate, int _numOfDays) {
        ReportSummary report = new ReportSummary();
        report.setName("Top Volumes By Average");
        report.setEndDate(_endDate);
        report.setStartDate(this.getStartDate(_endDate, _numOfDays));
        return report;
    }

    private ReportSummary getTopGainersByAmount(String _endDate, int _numOfDays) {
        ReportSummary report = new ReportSummary();
        report.setName("Top Gainers By Amount");
        report.setEndDate(_endDate);
        report.setStartDate(this.getStartDate(_endDate, _numOfDays));
        return report;
    }

    private ReportSummary getTopLosersByAmount(String _endDate, int _numOfDays) {
        ReportSummary report = new ReportSummary();
        report.setName("Top Losers By Amount");
        report.setEndDate(_endDate);
        report.setStartDate(this.getStartDate(_endDate, _numOfDays));
        return report;
    }

    private ReportSummary getTopGainersByPercentage(String _endDate, int _numOfDays) {
        ReportSummary report = new ReportSummary();
        report.setName("Top Gainers By Percentage");
        report.setEndDate(_endDate);
        report.setStartDate(this.getStartDate(_endDate, _numOfDays));
        return report;
    }

    private ReportSummary getTopLosersByPercentage(String _endDate, int _numOfDays) {
        ReportSummary report = new ReportSummary();
        report.setName("Top Losers By Percentage");
        report.setEndDate(_endDate);
        report.setStartDate(this.getStartDate(_endDate, _numOfDays));
        return report;
    }

    private String getStartDate(String _endDateString, int _numOfDays) {
        try {
            Date endDate = sdf.parse(_endDateString);
            Calendar endDateCalendar = Calendar.getInstance();
            endDateCalendar.setTime(endDate);
            endDateCalendar.add(Calendar.DAY_OF_YEAR, -1 * Math.abs(_numOfDays));
            String startDateString = sdf.format(endDateCalendar.getTime());
            return startDateString;
        } catch (ParseException ex) {
            Logger.getLogger(StockSummaryReportsController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @RequestMapping(value = "/dailyReport/volumes/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse getHighVolumes(@PathVariable("endDate") String endDate) {
        RestResponse restResponse = new RestResponse();
        List<StockHistory> stockHistories = new ArrayList<>();

        StockHistorySearchProperties shsp = new StockHistorySearchProperties();
        shsp.setSearchDate(endDate);
        shsp.setSortField("volume");
        shsp.setSortOrder("DESC");
        List<StockHistory> shs = this.stockHistorySearchRepo.searchForStockHistory(shsp);
        if (shs != null) {
            stockHistories.addAll(shs);
        }

        restResponse.setObject(stockHistories);
        return restResponse;
    }

    @RequestMapping(value = "/dailyReport/gainers-amount/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse getTopGainersByAmount(@PathVariable("endDate") String endDate) {
        RestResponse restResponse = new RestResponse();
        List<StockHistory> stockHistories = new ArrayList<>();

        StockHistorySearchProperties shsp = new StockHistorySearchProperties();
        shsp.setSearchDate(endDate);
        shsp.setSortField("actual_gain");
        shsp.setSortOrder("DESC");
        List<StockHistory> shs = this.stockHistorySearchRepo.searchForStockHistory(shsp);
        if (shs != null) {
            stockHistories.addAll(shs);
        }

        restResponse.setObject(stockHistories);
        return restResponse;
    }

    @RequestMapping(value = "/dailyReport/gainers-percent/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse getTopGainersByPercentage(@PathVariable("endDate") String endDate) {
        RestResponse restResponse = new RestResponse();
        List<StockHistory> stockHistories = new ArrayList<>();

        StockHistorySearchProperties shsp = new StockHistorySearchProperties();
        shsp.setSearchDate(endDate);
        shsp.setSortField("percent_gain");
        shsp.setSortOrder("DESC");
        List<StockHistory> shs = this.stockHistorySearchRepo.searchForStockHistory(shsp);
        if (shs != null) {
            stockHistories.addAll(shs);
        }

        restResponse.setObject(stockHistories);
        return restResponse;
    }

    @RequestMapping(value = "/dailyReport/losers-amount/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse getTopLosersByAmount(@PathVariable("endDate") String endDate) {
        RestResponse restResponse = new RestResponse();
        List<StockHistory> stockHistories = new ArrayList<>();

        StockHistorySearchProperties shsp = new StockHistorySearchProperties();
        shsp.setSearchDate(endDate);
        shsp.setSortField("actual_gain");
        shsp.setSortOrder("ASC");
        List<StockHistory> shs = this.stockHistorySearchRepo.searchForStockHistory(shsp);
        if (shs != null) {
            stockHistories.addAll(shs);
        }

        restResponse.setObject(stockHistories);
        return restResponse;
    }

    @RequestMapping(value = "/dailyReport/losers-percent/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse getTopLosersByPercentage(@PathVariable("endDate") String endDate) {
        RestResponse restResponse = new RestResponse();
        List<StockHistory> stockHistories = new ArrayList<>();

        StockHistorySearchProperties shsp = new StockHistorySearchProperties();
        shsp.setSearchDate(endDate);
        shsp.setSortField("percent_gain");
        shsp.setSortOrder("ASC");
        List<StockHistory> shs = this.stockHistorySearchRepo.searchForStockHistory(shsp);
        if (shs != null) {
            stockHistories.addAll(shs);
        }

        restResponse.setObject(stockHistories);
        return restResponse;
    }

}
