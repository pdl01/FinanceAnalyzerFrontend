/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.companynews;

import financialanalyzer.objects.Company;
import java.util.List;

/**
 *
 * @author pldor
 */
public interface CompanyNewsService {
    public List<CompanyNewsItem> getCompanyNewsItems(Company _company, int _numberOfArticlesPerProvider);
}
