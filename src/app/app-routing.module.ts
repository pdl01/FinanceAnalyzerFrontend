import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StocksearchComponent } from './stocksearch/stocksearch.component';
import { StockdetailsComponent } from './stockdetails/stockdetails.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'company/search', component: StocksearchComponent },
  { path: 'company/details/:id', component: StockdetailsComponent }

];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{ useHash: true }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
