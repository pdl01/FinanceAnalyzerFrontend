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

  newsIems: CompanyNewsItem[] = [];
  
  ngOnInit() {
          this.loadLatestNews();
  }
  loadLatestNews(): void {
    this.newsService.getLatestNews().subscribe(response => {
 
        if (response.code == 0) {
            this.newsIems = response.object;
        }
      });
  }

}
