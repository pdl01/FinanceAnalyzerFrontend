import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';

import { NewsService } from '../news.service';
import { CompanyNewsItem } from '../companynewsitem';
import { CompanyService } from '../company.service';

import { RestResponse } from '../restresponse';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  constructor(private route: ActivatedRoute,
  private newsService: NewsService,
  private companyService: CompanyService,
  private location: Location,
  private sanitizer: DomSanitizer    ) { }

  newsItems: CompanyNewsItem[] = [];
  private start = 0;
  private numResults = 25;

  sectorNames: string[] = [];
  selectedSector: string;
  industryNames: string[] = [];
  selectedIndustry: string;
  
  filterType: string = "latest";
  filterValue: string = "";
  
  viewedNewsItem: CompanyNewsItem = null;
  
  ngOnInit() {
    this.loadReferenceData();
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
  
  loadReferenceData(): void {
    this.loadSectorNames();
    this.loadIndustryNames();
  }
  loadSectorNames(): void {
      this.companyService.getSectorNames().subscribe(response => {
 
        if (response.code == 0) {
            this.sectorNames = response.object;
           
        }
      });
  }
  
  loadIndustryNames(): void {
      this.companyService.getIndustryNames().subscribe(response => {
 
        if (response.code == 0) {
            this.industryNames = response.object;
           
        }
      });  
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
  loadSectorBasedNews(_sector,start,results): void {
    this.newsService.getNewsForSectorStartingInRange(_sector,start,results).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = this.newsItems.concat(response.object);
           
        }
      });
  }  

loadIndustryBasedNews(_industry,start,results): void {
    this.newsService.getNewsForIndustryStartingInRange(_industry,start,results).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = this.newsItems.concat(response.object);
           
        }
      });
  }  


  loadStatusBasedNews(_statusField,_status,start,results): void {
    this.newsService.getNewsForStatusStartingInRange(_statusField,_status,start,results).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = this.newsItems.concat(response.object);
           
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
    } else if (this.filterType == 'text') {
      this.newsService.getNewsForTextSearchStartingInRange(this.filterValue,this.start,this.numResults).subscribe(response => {
 
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
        this.loadSectorBasedNews(this.selectedSector,this.start,this.numResults);
    } else if (this.filterType == 'industry') {
        this.loadIndustryBasedNews(this.selectedIndustry,this.start,this.numResults);
    } else if (this.filterType == 'unanalyzed') {
        this.loadStatusBasedNews("SYSTEM","NONE",this.start,this.numResults);
        //this.loadUnanalyzedNews();
    } else if (this.filterType == 'positive-user-rating') {
        this.loadStatusBasedNews("USER","POSITIVE",this.start,this.numResults);
    } else if (this.filterType == 'positive-system-rating') {
        this.loadStatusBasedNews("SYSTEM","POSITIVE",this.start,this.numResults);
    } else if (this.filterType == 'negative-user-rating') {
        this.loadStatusBasedNews("USER","NEGATIVE",this.start,this.numResults);
    } else if (this.filterType == 'negative-system-rating') {    
        this.loadStatusBasedNews("SYSTEM","NEGATIVE",this.start,this.numResults);

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
