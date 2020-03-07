/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.download;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.StockHistory;
import java.util.Date;
import java.util.List;
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
public class StockHistoryDowloadServiceImplTest {
    
    public StockHistoryDowloadServiceImplTest() {
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
     * Test of fetchDataForCompany method, of class StockHistoryDowloadServiceImpl.
     */
    @Test
    public void testFetchDataForCompany_Company() {
        System.out.println("fetchDataForCompany");
        Company company = null;
        StockHistoryDowloadServiceImpl instance = new StockHistoryDowloadServiceImpl();
        List<StockHistory> expResult = null;
        List<StockHistory> result = instance.fetchDataForCompany(company);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of fetchDataForCompany method, of class StockHistoryDowloadServiceImpl.
     */
    @Test
    public void testFetchDataForCompany_Company_Date() {
        System.out.println("fetchDataForCompany");
        Company company = null;
        Date _date = null;
        StockHistoryDowloadServiceImpl instance = new StockHistoryDowloadServiceImpl();
        List<StockHistory> expResult = null;
        List<StockHistory> result = instance.fetchDataForCompany(company, _date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
