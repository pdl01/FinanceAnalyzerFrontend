/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.StockHistory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldor
 */
public class AbstractCompanyProviderTest {

    public AbstractCompanyProviderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

  



    /**
     * Test of processTimeHistoryAlphavantageResult method, of class
     * AbstractCompanyProvider.
     */
    private String loadFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    @Test
    public void testProcessTimeHistoryAlphavantageResult() {
        System.out.println("processTimeHistoryAlphavantageResult");

        String _exchange = CompanyProvider.EXCHANGE_NASDAQ;
        String _symbol = "FB";
        String _date = null;
        String _jsonData = this.loadFile("/Users/pldor/Documents/Projects/financialPredictor/stockhistory_download_20200305.json");
        AbstractCompanyProvider instance = new AbstractCompanyProviderImpl();
        List<StockHistory> expResult = null;
        List<StockHistory> result = instance.processTimeHistoryAlphavantageResult(_exchange, _symbol, _date, _jsonData);
        assertNotNull(result);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    public class AbstractCompanyProviderImpl extends AbstractCompanyProvider {
    }

}
