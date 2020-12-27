import { Component, OnInit,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';
import { StockhistoryreportService } from '../stockhistoryreport.service';
import { StockHistory } from '../stockhistory';
import { StockPerformance } from '../stockperformance';
import { CompanyService } from '../company.service';

import {formatDate } from '@angular/common';

import { RestResponse } from '../restresponse';

@Component({
  selector: 'app-dailyreport',
  templateUrl: './dailyreport.component.html',
  styleUrls: ['./dailyreport.component.css']
})
export class DailyreportComponent implements OnInit {

  constructor(private route: ActivatedRoute,
  private stockhistoryreportService: StockhistoryreportService,
  private companyService: CompanyService,
  private location: Location,
  private sanitizer: DomSanitizer    ) {
       
       this.jstoday = formatDate(this.today, 'yyyy-MM-dd', 'en-US', 'EST');
       //console.log(this.jstoday);
  }
  endDate:string = '';
  previousDate:string='';
  nextDate:string='';
  
  today= new Date();
  jstoday = '';
  
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

  threedayPerfTopStocks: StockPerformance[] = [];
  private threedayPerfTopStocksStart = 0;

  private numResults = 10;
  ngOnInit() {

    const id = this.route.snapshot.paramMap.get('id');
    if (id == 'latest') {
        this.endDate = this.jstoday;
    } else {       
        this.endDate = id;
    }
    this.navigateToDate(this.endDate);
    
  }
  setupDates(): void {
    //console.log(this.endDate);
    var xDate = new Date(this.endDate+"T00:00:00");
    this.previousDate = formatDate(new Date().setDate(xDate.getDate()-1),'yyyy-MM-dd', 'en-US', 'EST');
    this.nextDate = formatDate(new Date().setDate(xDate.getDate()+1),'yyyy-MM-dd', 'en-US', 'EST');
    //console.log(this.previousDate);
    //console.log(this.nextDate);
  }
  
  navigateToDate(theDate): void {
    this.endDate = theDate;
    this.setupDates();
    this.loadReport(this.endDate,"volumes",this.volumeStocks);
    this.loadReport(this.endDate,"gainers-amount",this.gainerAmountStocks);
    this.loadReport(this.endDate,"gainers-percent",this.gainerPercentStocks);
    this.loadReport(this.endDate,"losers-amount",this.loserAmountStocks);
    this.loadReport(this.endDate,"losers-percent",this.loserPercentStocks);
    this.loadStockPerformanceReport(this.endDate,"threedayperf-DESC",this.threedayPerfTopStocks);  
  }
  triggerFetchForDate(): void {
    this.companyService.triggerStockDataFetchForDate(this.endDate).subscribe(response => {
    
    });
  }
  goToNextDate(): void {
      this.navigateToDate(this.nextDate);
  }
  goToPreviousDate(): void {
      this.navigateToDate(this.previousDate);
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
  
    loadStockPerformanceReport(endDate,reportName,objects): void {
     
     this.stockhistoryreportService.getStockPerformanceReportStartingWithNumberResults(endDate,reportName,0,this.numResults).subscribe(response => {
 
        if (response.code == 0) {
            if (reportName == 'threedayperf-DESC') {
                this.threedayPerfTopStocks = response.object;
            } else {
                objects = response.object;
            }
            
        }
      });
  }
  loadMoreStockPerformanceReportItems(reportName): void {
      
            var startToUse:number = 0;
            if (reportName == 'threedayperf-DESC') {
                this.volumeStart = this.volumeStart+this.numResults;
                startToUse = this.volumeStart;
            }
      
      
     this.stockhistoryreportService.getStockPerformanceReportStartingWithNumberResults(this.endDate,reportName,startToUse,this.numResults).subscribe(response => {
 
        if (response.code == 0) {
            if (reportName == 'threedayperf-DESC') {
                this.threedayPerfTopStocks = this.threedayPerfTopStocks.concat(response.object);
            }  
            
        }
      });
  }

}
