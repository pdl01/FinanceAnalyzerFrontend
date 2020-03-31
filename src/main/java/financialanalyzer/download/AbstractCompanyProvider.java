/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import financialanalyzer.http.HttpFetcher;
import financialanalyzer.http.HTMLPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import financialanalyzer.config.AppConfig;
import financialanalyzer.objects.Company;
import financialanalyzer.objects.StockHistory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pldor
 */
public abstract class AbstractCompanyProvider {

    private static final Logger LOGGER = Logger.getLogger(AdvfnAMEXCompanyProvider.class.getName());

    @Autowired
    protected HttpFetcher httpFetcher;

    @Autowired
    protected AppConfig appConfig;    
    
    protected List<StockHistory> downloadAndProcessCSVFromNasDaq(String _exchange, String _symbol, Date _date) {
        //https://www.nasdaq.com/api/v1/historical/BA/stocks/2020-03-01/2020-03-07
        String url = "https://www.nasdaq.com/api/v1/historical/::SYMBOL::/stocks/::MIN-DATE::/::MAX-DATE::";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date refDate = null;
        if (_date != null) {
            refDate = _date;
        } else {
            refDate = new Date();
        }

        String min_date = "";
        String max_date = "";
        if (refDate != null) {
            max_date = sdf.format(refDate);
            Calendar now = Calendar.getInstance();
            now.setTime(refDate);
            now.add(Calendar.DAY_OF_YEAR, -4);
            min_date = sdf.format(now.getTime());
        } 

        String resolvedURL = url.replaceAll("::SYMBOL::", _symbol).replaceAll("::MIN-DATE::", min_date).replaceAll("::MAX-DATE::", max_date);
        String downloadDirectoryPath = this.appConfig.getStockHistoryDownloadDir() + "/"+sdf.format(new Date());
        File downloadDirecory = new File (downloadDirectoryPath);
        downloadDirecory.mkdirs();
        String downloadFile = downloadDirectoryPath + "/" + _symbol + "-" + max_date + ".csv";
        boolean downloaded = this.downloadCSVForExchangeFromNasDaq(resolvedURL, downloadFile);
        LOGGER.info(downloaded + " : " + resolvedURL);

        List<StockHistory> shs = new ArrayList<>();
        shs = processStockHistoryExchangeCVS(_exchange, _symbol, _date, downloadFile);
        return shs;
    }

    protected boolean downloadCSVForExchangeFromNasDaq(String _url, String _fileName) {
        try {
            InputStream in = new URL(_url).openStream();
            Files.copy(in, Paths.get(_fileName), StandardCopyOption.REPLACE_EXISTING);
            File f = new File(_fileName);
            if (f.canRead()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    protected List<Company> processCSVForExchangedFromNasDaq(String _filename, String _exchange) {
        List<Company> companies = new ArrayList<>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(_filename));

            String[] line;
            int lineCounter = 0;
            while ((line = reader.readNext()) != null) {
                if (lineCounter != 0) {
                    Company company = new Company();
                    company.setName(line[1]);
                    company.setStockExchange(_exchange);
                    company.setStockSymbol(line[0]);
                    List<String> sectors = new ArrayList<>();
                    sectors.add(line[5]);
                    company.setSectors(sectors);
                    companies.add(company);
                }
                lineCounter++;
                //System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
            }
        } catch (IOException e) {
            LOGGER.severe("Unable to process csv:" + e.getMessage());
            e.printStackTrace();
        }

        return companies;
    }

    protected List<StockHistory> downloadTimeHistoryAlphavantage(String _exchange, String _symbol, Date _date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = null;
        if (_date != null) {
            dateString = sdf.format(_date);
        }
        return this.downloadTimeHistoryAlphavantage(_exchange, _symbol, dateString);
    }

    protected List<StockHistory> downloadTimeHistoryAlphavantage(String _exchange, String _symbol, String _date) {
        String api = "MUFPFXFF7DPXZ7B1";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=XX:STOCK_SYMBOL:XX&outputsize=compact&apikey=XX:API_KEY:XX";
        String resolvedurl = url.replaceAll("XX:STOCK_SYMBOL:XX", _symbol).replaceAll("XX:API_KEY:XX", api);
        //alpha advantage presents the more than just today's data, so need to return all
        LOGGER.info(resolvedurl);
        HTMLPage stockhistorypage = null;
        try {
            LOGGER.info("Starting Download of stock histories for :" + _symbol);
            stockhistorypage = this.httpFetcher.getResponse(resolvedurl, true);
            LOGGER.info("Completed Download of stock histories for :" + _symbol);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception fetching stock data for:" + _symbol, ex);

        }

        if (stockhistorypage.getStatusCode() != 200) {
            LOGGER.log(Level.SEVERE, "Non Success Error code when fetching stock data for:" + _symbol + ":" + stockhistorypage.getStatusCode());
            return null;
        }

        //LOGGER.info(stockhistorypage.getContent());
        if (stockhistorypage.getContent() == null) {
            return null;
        }
        return this.processTimeHistoryAlphavantageResult(_exchange, _symbol, _date, stockhistorypage.getContent());

    }

    protected List<StockHistory> processTimeHistoryAlphavantageResult(String _exchange, String _symbol, String _date, String _jsonData) {
        LOGGER.info("Beginning processTimeHistoryAlphavantageResult for " + _symbol);
        List<StockHistory> stockHistories = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (_jsonData == null) {
            LOGGER.log(Level.SEVERE, "NULL stock history data passed into processor for " + _symbol);

            return stockHistories;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            HashMap<String, Object> map = mapper.readValue(_jsonData, HashMap.class);
            if (map == null) {
                LOGGER.log(Level.SEVERE, "Unable to process feed for " + _symbol);
                return null;
            }
            HashMap<String, Object> dailyMap = (HashMap<String, Object>) map.get("Time Series (Daily)");
            if (dailyMap == null) {
                LOGGER.log(Level.SEVERE, "Unable to get Root feed element for " + _symbol);
                return null;
            }
            Set<String> dailyMapKeys = dailyMap.keySet();
            for (String dailyMapKey : dailyMapKeys) {
                HashMap<String, Object> dayMap = (HashMap<String, Object>) dailyMap.get(dailyMapKey);
                StockHistory sh = new StockHistory();
                //populate stock history
                sh.setExchange(_exchange);
                sh.setSymbol(_symbol);
                sh.setRecordDate(sdf.parse(dailyMapKey));
                sh.setOpen(Float.parseFloat((String) dayMap.get("1. open")));
                sh.setClose(Float.parseFloat((String) dayMap.get("4. close")));
                sh.setHigh(Float.parseFloat((String) dayMap.get("2. high")));
                sh.setLow(Float.parseFloat((String) dayMap.get("3. low")));
                sh.setVolume(Integer.parseInt((String) dayMap.get("5. volume")));
                sh.setActual_gain(sh.getClose() - sh.getOpen());
                sh.setPercent_gain(sh.getActual_gain() / sh.getOpen());
                //LOGGER.log(Level.INFO, dailyMapKey);
                stockHistories.add(sh);
            }
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, "Exception processing feed for :" + _symbol, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception processing feed for :" + _symbol, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception processing feed for :" + _symbol, ex);
        }
        LOGGER.info("Completed processTimeHistoryAlphavantageResult for " + _symbol);
        return stockHistories;
    }

    protected List<StockHistory> processStockHistoryExchangeCVS(String _exchange, String _symbol, Date _date, String _filename) {
        List<StockHistory> shs = new ArrayList<>();
        CSVReader reader = null;
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");

        try {
            reader = new CSVReader(new FileReader(_filename));

            String[] line;
            int lineCounter = 0;
            while ((line = reader.readNext()) != null) {
                if (lineCounter != 0) {
                    StockHistory sh = new StockHistory();
                    sh.setSymbol(_symbol);
                    sh.setExchange(_exchange);

                    sh.setRecordDate(sdf.parse(line[0]));
                    sh.setOpen(Float.parseFloat(line[3].replaceAll("\\$", "")));
                    sh.setClose(Float.parseFloat(line[1].replaceAll("\\$", "")));
                    sh.setHigh(Float.parseFloat(line[4].replaceAll("\\$", "")));
                    sh.setLow(Float.parseFloat(line[5].replaceAll("\\$", "")));
                    if (!line[2].contains("N/A")) {
                        sh.setVolume(Integer.parseInt(line[2].replaceAll(" ", "")));

                    }
                    sh.setActual_gain(sh.getClose() - sh.getOpen());
                    sh.setPercent_gain(sh.getActual_gain() / sh.getOpen());
                    shs.add(sh);
                }
                lineCounter++;
                //System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
            }
        } catch (ParseException e) {
            LOGGER.severe("Unable to parse date in csv:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {

            LOGGER.severe("Unable to process csv:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.severe(_symbol + " : Unable to process csv:" + e.getMessage());
            e.printStackTrace();
        }

        return shs;
    }
}
