import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { HttpClientModule }    from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { StocksearchComponent } from './stocksearch/stocksearch.component';
import { StockdetailsComponent } from './stockdetails/stockdetails.component';
import { DailyreportComponent } from './dailyreport/dailyreport.component';
import { NewsComponent } from './news/news.component';

@NgModule({
  declarations: [
    AppComponent,
    StocksearchComponent,
    StockdetailsComponent,
    DailyreportComponent,
    NewsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
