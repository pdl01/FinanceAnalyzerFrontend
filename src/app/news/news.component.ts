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
  
  ngOnInit() {
          this.loadLatestNews();
  }
  loadLatestNews(): void {
    this.newsService.getLatestNews().subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = response.object;
           
        }
      });
  }

  loadMoreNews(): void {
    this.start = this.start+this.numResults;
    this.newsService.getLatestNewsStartingInRange(this.start,this.numResults).subscribe(response => {
 
        if (response.code == 0) {
            this.newsItems = this.newsItems.concat(response.object);
            
            //this.newsItems = [ ...this.newsItems, ...response.object];
        }
      });
  
  }
  
}
