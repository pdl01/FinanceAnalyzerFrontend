import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 
import {environment} from '../environments/environment';
import { SystemHealthRecord } from './systemhealthrecord';

@Injectable({
  providedIn: 'root'
})
export class SystemHealthService {

 constructor( private http: HttpClient) { }
 private apiRoot = environment.serverUrl+'/api/v1';
 
  getListOfReports(): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/systemhealth/recordlist').pipe();    
  }
  getReport(reportName): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/systemhealth/item/'+reportName).pipe();    
  }
  buildReport(): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/systemhealth/latest',{},{}).pipe();    
  }
  buildReportAndFixData(): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/systemhealth/latest/rebuild',{},{}).pipe();    
  }
  fillGap(dateAsString): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/systemhealth/gaps/day/'+dateAsString,{},{}).pipe();
  }
 
}
