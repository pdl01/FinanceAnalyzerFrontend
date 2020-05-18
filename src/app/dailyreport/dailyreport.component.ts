import { Component, OnInit,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';
import { StockhistoryreportService } from '../stockhistoryreport.service';
import { StockHistory } from '../stockhistory';

import { RestResponse } from '../restresponse';

@Component({
  selector: 'app-dailyreport',
  templateUrl: './dailyreport.component.html',
  styleUrls: ['./dailyreport.component.css']
})
export class DailyreportComponent implements OnInit {

  constructor(private route: ActivatedRoute,
  private stockhistoryreportService: StockhistoryreportService,
  private location: Location,
  private sanitizer: DomSanitizer    ) { }
  endDate:string = '';
  
  volumeStocks: StockHistory[] = [];
  private volumeStart = 0;
   
  gainerAmountStocks: StockHistory[] = [];
  private gainerAmountStart = 0;
  
  gainerPercentStocks: StockHistory[] = [];
  private gainerPercentStart = 0;
  
  loserAmountStocks: StockHistory[] = [];
  private loserAmountStart = 0;
  
  loserPercentStocks: StockHistory[] = [];
  private loserPercentStart = 0;

  private numResults = 25;
  ngOnInit() {

    const id = this.route.snapshot.paramMap.get('id');
    this.endDate = id;
    this.loadReport(id,"volumes",this.volumeStocks);
    this.loadReport(id,"gainers-amount",this.gainerAmountStocks);
    this.loadReport(id,"gainers-percent",this.gainerPercentStocks);
    this.loadReport(id,"losers-amount",this.loserAmountStocks);
    this.loadReport(id,"losers-percent",this.loserPercentStocks);
  }
  loadReport(endDate,reportName,objects): void {
     
     this.stockhistoryreportService.getStockHistoryReportStartingWithNumberResults(endDate,reportName,0,this.numResults).subscribe(response => {
 
        if (response.code == 0) {
            if (reportName == 'volumes') {
                this.volumeStocks = response.object;
            } else if (reportName == 'gainers-amount') {
                this.gainerAmountStocks = response.object;
            } else if (reportName == 'gainers-percent') {
                this.gainerPercentStocks = response.object;
            } else if (reportName == 'losers-amount') {
                this.loserAmountStocks = response.object;
            } else if (reportName == 'losers-percent') {
                this.loserPercentStocks = response.object;
            } else {
                objects = response.object;
            }
            
        }
      });
  }

  loadMoreReportItems(reportName): void {
      
            var startToUse:number = 0;
            if (reportName == 'volumes') {
                this.volumeStart = this.volumeStart+this.numResults;
                startToUse = this.volumeStart;
            } else if (reportName == 'gainers-amount') {
                this.gainerAmountStart = this.gainerAmountStart+this.numResults;
                startToUse = this.gainerAmountStart;

            } else if (reportName == 'gainers-percent') {
                this.gainerPercentStart = this.gainerPercentStart+this.numResults;
                startToUse = this.gainerPercentStart;
            } else if (reportName == 'losers-amount') {
                this.loserAmountStart = this.loserAmountStart+this.numResults;
                startToUse = this.loserAmountStart;
            } else if (reportName == 'losers-percent') {
                this.loserPercentStart = this.loserPercentStart+this.numResults;
                startToUse = this.loserPercentStart;
            }
      
      
     this.stockhistoryreportService.getStockHistoryReportStartingWithNumberResults(this.endDate,reportName,startToUse,this.numResults).subscribe(response => {
 
        if (response.code == 0) {
            if (reportName == 'volumes') {
                this.volumeStocks = this.volumeStocks.concat(response.object);
            } else if (reportName == 'gainers-amount') {
                this.gainerAmountStocks = this.gainerAmountStocks.concat(response.object);
            } else if (reportName == 'gainers-percent') {
                this.gainerPercentStocks = this.gainerPercentStocks.concat(response.object);
            } else if (reportName == 'losers-amount') {
                this.loserAmountStocks = this.loserAmountStocks.concat(response.object);
            } else if (reportName == 'losers-percent') {
                this.loserPercentStocks = this.loserPercentStocks.concat(response.object);
            } 
            
        }
      });
  }
}
