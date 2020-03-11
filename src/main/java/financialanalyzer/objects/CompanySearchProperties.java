/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.objects;

/**
 *
 * @author pldor
 */
public class CompanySearchProperties {
    private int numResults =100;
    private int startResults =0;
    
    private String companyName;
    private String stockExchange;
    private String stockSymbol;
    private String companyId;

    public int getNumResults() {
        return numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getStartResults() {
        return startResults;
    }

    public void setStartResults(int startResults) {
        this.startResults = startResults;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    
}
