import { Component, OnInit,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { CompanyService } from '../company.service';

import { Company } from '../company';
import { RestResponse } from '../restresponse';

@Component({
  selector: 'app-stocksearch',
  templateUrl: './stocksearch.component.html',
  styleUrls: ['./stocksearch.component.css']
})
export class StocksearchComponent implements OnInit {
  searchCompanyItems: Company[];
  searchTerm: string;
  searchType: string;
  companySearchStart = 0;
  beginningSearch: boolean = true;
  constructor(private companyService: CompanyService) { }

  ngOnInit() {
  }

  doSimpleSearch(): void {
    this.companySearchStart = 0;
    
    this.beginningSearch = true;
    console.log(this.searchType);
    if (this.searchType != 'name') {
    
this.companyService.searchCompaniesBySymbol(this.searchTerm,0,100).subscribe(response => {
        if (response.code == 0) {
            //
            if (this.searchCompanyItems == null) {
                if (response.object == null) {

                } else {
                    
                    this.searchCompanyItems = response.object;
                }
                
            
            } else {
               
               if (this.beginningSearch == true) {
                    this.searchCompanyItems = response.object;
               } else {
                 this.searchCompanyItems = this.searchCompanyItems.concat(response.object);
               }
               
               
            
            
            }
}
        }
    );

    } else {
    
    this.companyService.searchCompaniesByName(this.searchTerm,0,100).subscribe(response => {
        if (response.code == 0) {
            //
            if (this.searchCompanyItems == null) {
                if (response.object == null) {

                } else {
                    
                    this.searchCompanyItems = response.object;
                }
                
            
            } else {
               
               if (this.beginningSearch == true) {
                    this.searchCompanyItems = response.object;
               } else {
                 this.searchCompanyItems = this.searchCompanyItems.concat(response.object);
               }
               
               
            
            
            }
}
        }
    );
}
  }

}
