import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';

import { NewsService } from '../news.service';
import { CompanyNewsItem } from '../companynewsitem';

import { RestResponse } from '../restresponse';
@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  constructor(private route: ActivatedRoute,
  private newsService: NewsService,
  private location: Location,
  private sanitizer: DomSanitizer    ) { }

  newsItems: CompanyNewsItem[] = [];
  private start = 0;
  private numResults = 25;
  
  filterType: string = "latest";
  filterValue: string = "";
  
  viewedNewsItem: CompanyNewsItem = null;
  
  ngOnInit() {
          this.performInitialNewsFilter();
  }
  loadLatestNews(): void {
    this.newsService.getLatestNews().subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = response.object;
           
        }
      });
  }
    
  performInitialNewsFilter(): void {
    this.start = -1*(this.numResults);
    this.newsItems = [];
    this.loadMoreNews();
     
    
  }
  
  loadDateBasedNews(): void {
    this.newsService.getLatestNews().subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = response.object;
           
        }
      });
  }
  
  loadSymbolBasedNews(): void {
    this.newsService.getLatestNews().subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = response.object;
           
        }
      });
  }
  loadSectorBasedNews(): void {
    this.newsService.getLatestNews().subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = response.object;
           
        }
      });
  }  

  loadStatusBasedNews(_statusField,_status): void {
    this.newsService.getNewsForStatus(_statusField,_status).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = response.object;
           
        }
      });
  }  
  
  loadNews(filterType,filterValue,start,numResults): void {
    
  }
  
  loadMoreNews(): void {
    this.start = this.start+this.numResults;
    
    if (this.filterType == 'latest') {
      this.newsService.getLatestNewsStartingInRange(this.start,this.numResults).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = this.newsItems.concat(response.object);
            
            //this.newsItems = [ ...this.newsItems, ...response.object];
        }
      });
    } else if (this.filterType == 'date') {
      this.newsService.getNewsForDateStartingInRange(this.filterValue,this.start,this.numResults).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = this.newsItems.concat(response.object);
            
            //this.newsItems = [ ...this.newsItems, ...response.object];
        }
      });
    } else if (this.filterType == 'symbol') {
      this.newsService.getCompanyNewsStartingInRange(this.filterValue,this.start,this.numResults).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = this.newsItems.concat(response.object);
            
            //this.newsItems = [ ...this.newsItems, ...response.object];
        }
      });
    } else if (this.filterType == 'sector') {
        //this.loadSectorBasedNews();
    } else if (this.filterType == 'unanalyzed') {
        this.loadStatusBasedNews("SYSTEM","NONE");
        //this.loadUnanalyzedNews();
    } else if (this.filterType == 'positive-user-rating') {
        this.loadStatusBasedNews("USER","POSITIVE");
    } else if (this.filterType == 'positive-system-rating') {
        this.loadStatusBasedNews("SYSTEM","POSITIVE");
    } else if (this.filterType == 'negative-user-rating') {
        this.loadStatusBasedNews("USER","NEGATIVE");
    } else if (this.filterType == 'negative-system-rating') {    
        this.loadStatusBasedNews("SYSTEM","POSITIVE");

    } 
    
      
 
  
  }
  
  viewNewsItem(newsItem:CompanyNewsItem): void {
      //console.log("in viewText");
      //console.log(newsItem.body);
    this.viewedNewsItem = newsItem;
    // = newsItem.body;
    //console.log(this.newsItemText);
  }
  
  clearViewedNewsItem(): void {
      this.viewedNewsItem = null;
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
}
