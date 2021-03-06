import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StocksearchComponent } from './stocksearch/stocksearch.component';
import { StockdetailsComponent } from './stockdetails/stockdetails.component';
import { DailyreportComponent } from './dailyreport/dailyreport.component'
import { NewsComponent } from './news/news.component'
import { DashboardComponent } from './dashboard/dashboard.component';
import { SystemhealthComponent } from './systemhealth/systemhealth.component';
import { SectorComponent } from './sector/sector.component';
import { IndustryComponent } from './industry/industry.component';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'company/search', component: StocksearchComponent },
  { path: 'news', component: NewsComponent },
  { path: 'company/details/:id', component: StockdetailsComponent },
  { path: 'dailyreport/:id', component: DailyreportComponent },
  { path: 'dailyreport/:id/:tab', component: DailyreportComponent }, 
  { path: 'dashboard', component: DashboardComponent },
  { path: 'systemhealth', component: SystemhealthComponent },
  { path: 'sector/details/:id', component: SectorComponent },
  { path: 'industry/details/:id', component: IndustryComponent }

];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{ useHash: true }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
