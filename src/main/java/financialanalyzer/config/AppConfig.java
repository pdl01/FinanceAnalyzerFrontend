/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.config;

import java.text.SimpleDateFormat;

/**
 *
 * @author pldor
 */
public class AppConfig {

    public final static String stockHistoryDownloadDir = "/temp/fa/work/stockdownloads";
    public final static String companyDownloadDir = "/temp/fa/work/companydownloads";
    public final static String companyReportDownloadDir = "/temp/fa/work/companyReportDownloads";
    public final static String companyNewsDownloadDir = "/temp/fa/work/companyNewsDownloads";

    public final static SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd");
}
