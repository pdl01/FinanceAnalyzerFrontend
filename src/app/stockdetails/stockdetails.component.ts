import { Component, OnInit,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';

import { CompanyService } from '../company.service';

import { Company } from '../company';
import { StockHistory } from '../stockhistory';
import { SystemActivity } from '../systemactivity';
import { CompanyNewsItem } from '../companynewsitem';

import { RestResponse } from '../restresponse';

@Component({
  selector: 'app-stockdetails',
  templateUrl: './stockdetails.component.html',
  styleUrls: ['./stockdetails.component.css']
})
export class StockdetailsComponent implements OnInit {

  constructor(private route: ActivatedRoute,
  private companyService: CompanyService,
  private location: Location,
  private sanitizer: DomSanitizer    ) { }
  
  company: Company;
  
  suppliers: Company[] = [];
  producers: Company[] = [];
  stockHistories: StockHistory[] = [];
  systemActivities: SystemActivity[] = [];
  newsIems: CompanyNewsItem[] = [];

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.loadCompany(id);
    this.loadStocks(id);
    this.loadSystemActivity(id);
    this.loadCompanyNews(id);
  }

  loadCompany(id): void {
     this.companyService.getCompanyById(id).subscribe(response => {
 
        if (response.code == 0) {
            this.company = response.object[0];
        }
      });
  }
  loadStocks(id): void {
    this.companyService.getStockHistory(id).subscribe(response => {
 
        if (response.code == 0) {
            this.stockHistories = response.object;
        }
      });
  }
  loadSystemActivity(id): void {
    this.companyService.getSystemActivity(id).subscribe(response => {
 
        if (response.code == 0) {
            this.systemActivities = response.object;
        }
      });
  }
  loadCompanyNews(id): void {
    this.companyService.getCompanyNews(id).subscribe(response => {
 
        if (response.code == 0) {
            this.newsIems = response.object;
        }
      });
  }
  fetchStockForCompany(): void {
    this.companyService.fetchStockData(this.company.stockSymbol).subscribe();
  }
}
