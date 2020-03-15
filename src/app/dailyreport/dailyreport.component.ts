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

  private volumeStocks: StockHistory[] = [];
  private gainerAmountStocks: StockHistory[] = [];
  private gainerPercentStocks: StockHistory[] = [];
  private loserAmountStocks: StockHistory[] = [];
  private loserPercentStocks: StockHistory[] = [];

  ngOnInit() {

    const id = this.route.snapshot.paramMap.get('id');
    this.loadReport(id,"volumes",this.volumeStocks);
    this.loadReport(id,"gainers-amount",this.gainerAmountStocks);
    this.loadReport(id,"gainers-percent",this.gainerPercentStocks);
    this.loadReport(id,"losers-amount",this.loserAmountStocks);
    this.loadReport(id,"losers-percent",this.loserPercentStocks);
  }
  loadReport(endDate,reportName,objects): void {
     this.stockhistoryreportService.getStockHistoryReport(endDate,reportName).subscribe(response => {
 
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
}
