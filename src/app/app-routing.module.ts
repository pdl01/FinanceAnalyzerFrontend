import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StocksearchComponent } from './stocksearch/stocksearch.component';
import { StockdetailsComponent } from './stockdetails/stockdetails.component';
import { DailyreportComponent } from './dailyreport/dailyreport.component'
import { NewsComponent } from './news/news.component'
import { DashboardComponent } from './dashboard/dashboard.component';
import { SystemhealthComponent } from './systemhealth/systemhealth.component';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'company/search', component: StocksearchComponent },
  { path: 'news', component: NewsComponent },
  { path: 'company/details/:id', component: StockdetailsComponent },
  { path: 'dailyreport/:id', component: DailyreportComponent },
  { path: 'dashboard', component: DashboardComponent },
    { path: 'systemhealth', component: SystemhealthComponent }

];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{ useHash: true }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
