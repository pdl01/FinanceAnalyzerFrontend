/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import com.opencsv.CSVReader;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author pldor
 */
public abstract class AbstractCompanyProvider {

    private static final Logger LOGGER = Logger.getLogger(AdvfnAMEXCompanyProvider.class.getName());

    protected boolean downloadCSVForExchangeFromNasDaq(String _url, String _fileName) {
        try {
            InputStream in = new URL(_url).openStream();
            Files.copy(in, Paths.get(_fileName), StandardCopyOption.REPLACE_EXISTING);
            File f = new File (_fileName);
            if (f.canRead()) {
                return true;
            }        
            return false;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }
    protected List<Company> processCSVForExchangedFromNasDaq(String _filename,String _exchange) {
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
            LOGGER.severe("Unable to process csv:"+e.getMessage());
            e.printStackTrace();
        }        
        
        
        return companies;
    }
    
    protected List<StockHistory> downloadTimeHistoryAlphavantage(String _symbol,String _date) {
        List<StockHistory> stockHistories = new ArrayList<>();
        String api = "MUFPFXFF7DPXZ7B1";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=XX:STOCK_SYMBOL:XX&outputsize=compact&apikey=XX:API_KEY:XX";
        String resolvedurl = url.replaceAll("XX:STOCK_SYMBOL:XX", _symbol).replaceAll("XX:API_KEY:XX", api);
        LOGGER.info(resolvedurl);
        
        return stockHistories;
    }
}
