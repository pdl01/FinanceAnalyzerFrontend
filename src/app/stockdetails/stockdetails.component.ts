import { Component, OnInit,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';

import { CompanyService } from '../company.service';
import { NewsService } from '../news.service';

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
  private newsService: NewsService,
  private location: Location,
  private sanitizer: DomSanitizer    ) { }
  
  company: Company;
  
  suppliers: Company[] = [];
  producers: Company[] = [];
  stockHistories: StockHistory[] = [];
  systemActivities: SystemActivity[] = [];
  newsIems: CompanyNewsItem[] = [];
  newsItemText: string = null;
  
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.loadCompany(id);
    this.loadStockHistory(id);
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
  loadStockHistory(id): void {
    this.companyService.getStockHistory(id).subscribe(response => {
 
        if (response.code == 0) {
            this.stockHistories = response.object;
        }
      });
  }
  loadMoreStockHistory(): void {
  }
  
  loadSystemActivity(id): void {
    this.companyService.getSystemActivity(id).subscribe(response => {
 
        if (response.code == 0) {
            this.systemActivities = response.object;
        }
      });
  }
  loadMoreSystemActivity(): void {
  }
  
  loadCompanyNews(id): void {
    this.newsService.getCompanyNews(id).subscribe(response => {
 
        if (response.code == 0) {
            this.newsIems = response.object;
        }
      });
  }
  
  loadMoreNews(): void {
  }
  
  
  viewNewsItemText(newsItem:CompanyNewsItem): void {
    this.newsItemText = newsItem.body;
  }
  
  clearNewsItemText(): void {
      this.newsItemText = null;
  }
  
  fetchStockForCompany(): void {
    this.companyService.fetchStockData(this.company.stockSymbol).subscribe();
  }
  fetchNewsForCompany(): void {
    this.newsService.fetchNewsForCompany(this.company.stockSymbol).subscribe();
  }
}
