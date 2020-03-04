/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.respository;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.CompanySearchProperties;
import java.util.List;

/**
 *
 * @author pldor
 */
public interface CompanyRepo {
    public Company submit(Company _company);
    public boolean delete(Company _company);
    public List<Company> searchForCompany(CompanySearchProperties _csp);
}
