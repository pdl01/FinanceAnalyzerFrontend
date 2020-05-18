import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StockhistoryreportService {

  constructor( private http: HttpClient) { }
 private apiRoot = environment.serverUrl+'/api/v1';

  getStockHistoryReport(endDate,reportName): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/reports/stocks/dailyReport/'+reportName+'/'+endDate).pipe();    
  }  
  getStockHistoryReportStartingWith(endDate,reportName,start): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/reports/stocks/dailyReport/'+reportName+'/'+endDate+'/'+start).pipe();    
  }  
  getStockHistoryReportStartingWithNumberResults(endDate,reportName,start,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/reports/stocks/dailyReport/'+reportName+'/'+endDate+'/'+start+'/'+numResults).pipe();    
  }  
}
