import { Component, OnInit,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';

import { CompanyService } from '../company.service';
import { NewsService } from '../news.service';

import { Company } from '../company';
import { CompanyNewsItem } from '../companynewsitem';

import { RestResponse } from '../restresponse';


@Component({
  selector: 'app-industry',
  templateUrl: './industry.component.html',
  styleUrls: ['./industry.component.css']
})
export class IndustryComponent implements OnInit {

  constructor(private route: ActivatedRoute,
  private companyService: CompanyService,
  private newsService: NewsService,
  private location: Location,
  private sanitizer: DomSanitizer) { }

  industry: String;
  
  companies: Company[] = [];
  newsItems: CompanyNewsItem[] = [];
  
  
  viewedNewsItem: CompanyNewsItem = null;

  ngOnInit() {
    const industry = this.route.snapshot.paramMap.get('id');
    this.industry = decodeURIComponent(industry);
    this.loadCompanies(this.industry);
    this.loadNews(this.industry);
  }

  loadNews(industry): void {
    this.newsService.getNewsForIndustry(industry).subscribe(response => {
 
        if (response.code == 0) {
           this.newsItems = response.object;
        }
      });
  }
  loadCompanies(industry): void {
    this.companyService.searchCompaniesByIndustry(industry,0,25).subscribe(response => {
 
        if (response.code == 0) {
           this.companies = response.object;
        }
      });
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
