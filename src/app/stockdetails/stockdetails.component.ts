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
import { StockPerformance } from '../stockperformance';
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
  newsItems: CompanyNewsItem[] = [];
  stockPerformances: StockPerformance[] = [];
  
  viewedNewsItem: CompanyNewsItem = null;

  private newsStart = 0;
  private newsNumResults = 25;
  
  
  
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.loadCompany(id);
    this.loadStockPerformance(id);
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

  loadStockPerformance(id): void {
    this.companyService.getStockPerformance(id).subscribe(response => {
 
        if (response.code == 0) {
            this.stockPerformances = response.object;
        }
      });
  }
  
  loadMoreStockPerformances(): void {
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
    this.newsService.getCompanyNewsStartingInRange(id,this.newsStart,this.newsNumResults).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = response.object;
        }
      });
  }
  
  loadMoreNews(): void {
      this.newsStart = this.newsStart+this.newsNumResults;
      this.newsService.getCompanyNewsStartingInRange(this.company.id,this.newsStart,this.newsNumResults).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = this.newsItems.concat(response.object);
            
            //this.newsItems = [ ...this.newsItems, ...response.object];
        }
      });
      
  }
  
  
  viewNewsItem(newsItem:CompanyNewsItem): void {
    this.viewedNewsItem = newsItem;
  }
  
  clearViewedNewsItem(): void {
      this.viewedNewsItem = null;
  }
  
  fetchStockForCompany(): void {
    this.companyService.fetchStockData(this.company.stockSymbol).subscribe();
  }
  fetchNewsForCompany(): void {
    this.newsService.fetchNewsForCompany(this.company.stockSymbol).subscribe();
  }
  
  updateDownloadOptions(): void {
     this.companyService.updateCompanyDownloadOptions(this.company).subscribe(response => {
     
     });
     console.log("changed");
  }    

  updateUserRating(userRating): void {
    if (this.viewedNewsItem != null) {
        this.viewedNewsItem.userRating = userRating;
        this.newsService.updateUserRating(this.viewedNewsItem.id,this.viewedNewsItem.userRating).subscribe(response => {
 
        if (response.code == 0) {
            //this.newsItems = this.newsItems.concat(response.object);
            
            //this.newsItems = [ ...this.newsItems, ...response.object];
        }
        this.clearViewedNewsItem();
      });
    }
  }
  encodeName(name): String{
        //return name.replace("\/","%2F");
      return encodeURIComponent(name);
  }
  generateSystemRating(): void{
      if (this.viewedNewsItem != null) {
          this.newsService.generateSentimentAnalysis(this.viewedNewsItem.id).subscribe(response => {
 
            if (response.code == 0) {
            }
          });
      }
  }
      
}
