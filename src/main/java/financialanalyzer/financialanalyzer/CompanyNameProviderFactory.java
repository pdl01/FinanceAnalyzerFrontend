/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.financialanalyzer;

import org.springframework.stereotype.Component;

import financialanalyzer.download.CompanyProvider;

/**
 *
 * @author pldor
 */
@Component
public class CompanyNameProviderFactory {
	
	
    public CompanyProvider getCompanyProvider(String _exchange) {
    	return null;
    }
}
