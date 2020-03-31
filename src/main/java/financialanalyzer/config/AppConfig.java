/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.config;

import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author pldor
 */
@Configuration
@PropertySource(value = "file:${app_home}/config/application.properties")
public class AppConfig {
    
    @Value("${dir.download.stockHistory}")
    private String stockHistoryDownloadDir;
    //public final static String stockHistoryDownloadDir = "/tmp/fa/work/stockdownloads";
    
    @Value("${dir.download.companies}")
    private String companyDownloadDir;
    //public final static String companyDownloadDir = "/tmp/fa/work/companydownloads";
    
    @Value("${dir.download.companyreport}")
    private String companyReportDir;
    //public final static String companyReportDownloadDir = "/tmp/fa/work/companyReportDownloads";
    
    @Value("${dir.download.companynews}")
    private String companyNewsDownloadDir;
    //public final static String companyNewsDownloadDir = "/tmp/fa/work/companyNewsDownloads";

    public final static SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String getStockHistoryDownloadDir() {
        return stockHistoryDownloadDir;
    }

    public void setStockHistoryDownloadDir(String stockHistoryDownloadDir) {
        this.stockHistoryDownloadDir = stockHistoryDownloadDir;
    }

    public String getCompanyDownloadDir() {
        return companyDownloadDir;
    }

    public void setCompanyDownloadDir(String companyDownloadDir) {
        this.companyDownloadDir = companyDownloadDir;
    }

    public String getCompanyReportDir() {
        return companyReportDir;
    }

    public void setCompanyReportDir(String companyReportDir) {
        this.companyReportDir = companyReportDir;
    }

    public String getCompanyNewsDownloadDir() {
        return companyNewsDownloadDir;
    }

    public void setCompanyNewsDownloadDir(String companyNewsDownloadDir) {
        this.companyNewsDownloadDir = companyNewsDownloadDir;
    }
    
    
    
}
