
package financialanalyzer.objects;

import java.util.List;

/**
 *
 * @author pldor
 */
public class Company {
    private String id;
    private String name;
    private String stockSymbol;
    private String stockExchange;
    private List<String> sectors;

    public String getId() {
        return this.stockExchange+"-"+this.stockSymbol;
    }

    public void setId(String id) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public List<String> getSectors() {
        return sectors;
    }

    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }
    
    
}
