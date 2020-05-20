import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

constructor( private http: HttpClient) { }
 private apiRoot = environment.serverUrl+'/api/v1';
   getCompanyNews(id): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/company/'+id).pipe();    
  }
  getCompanyNewsStarting(id,starting): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/company/'+id+'/'+starting).pipe();    
  }
  getCompanyNewsStartingInRange(id,starting,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/company/'+id+'/'+starting+'/'+numResults).pipe();    
  }
  getLatestNews(): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/latest').pipe();    
  }
  getLatestNewsStarting(starting): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/latest/'+starting).pipe();    
  }  
  getLatestNewsStartingInRange(starting,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/latest/'+starting+'/'+numResults).pipe();    
  }
  getNewsForDate(_date): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/date/'+_date).pipe();    
  }
  getNewsForDateStarting(_date,starting): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/date/'+_date+'/'+starting).pipe();    
  }  
  getNewsForDateStartingInRange(_date,starting,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/date/'+_date+ '/'+starting+'/'+numResults).pipe();    
  }
    
  fetchNewsForCompany(id): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/news/symbol/'+id+'/fetch',null,{}).pipe(); 
  }
}
