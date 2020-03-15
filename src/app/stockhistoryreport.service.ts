import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 

@Injectable({
  providedIn: 'root'
})
export class StockhistoryreportService {

  constructor( private http: HttpClient) { }
 private apiRoot = 'http://localhost:8080/api/v1';

  getStockHistoryReport(endDate,reportName): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/reports/stocks/dailyReport/'+reportName+'/'+endDate).pipe();    
  }  
}
