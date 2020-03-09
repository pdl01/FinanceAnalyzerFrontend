/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author pldor
 */
public class StockHistoryDownloadTask implements Serializable {
    private String exchange;
    private String symbol;
    private Date retrieveDate;
    private boolean downloadAllAvailalble;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getRetrieveDate() {
        return retrieveDate;
    }

    public void setRetrieveDate(Date retrieveDate) {
        this.retrieveDate = retrieveDate;
    }



    public boolean isDownloadAllAvailalble() {
        return downloadAllAvailalble;
    }

    public void setDownloadAllAvailalble(boolean downloadAllAvailalble) {
        this.downloadAllAvailalble = downloadAllAvailalble;
    }
    
}
