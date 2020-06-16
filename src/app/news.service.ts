import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 
import {environment} from '../environments/environment';
import {NewsItemForm} from './newsitemform';

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

  getNewsForSector(_sector): Observable<RestResponse> {
      return this.getNewsForSectorStarting(_sector,0);    
  }
  getNewsForSectorStarting(_sector,starting): Observable<RestResponse> {
      return this.getNewsForSectorStartingInRange(_sector,starting,25);    
  }  
  getNewsForSectorStartingInRange(_sector,starting,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/sector/'+encodeURIComponent(_sector)+ '/'+starting+'/'+numResults).pipe();    
  }  

  getNewsForIndustry(_industry): Observable<RestResponse> {
      return this.getNewsForIndustryStarting(_industry,0);    
  }
  getNewsForIndustryStarting(_industry,starting): Observable<RestResponse> {
      return this.getNewsForIndustryStartingInRange(_industry,starting,25);    
  }  
  getNewsForIndustryStartingInRange(_industry,starting,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/industry/'+encodeURIComponent(_industry)+ '/'+starting+'/'+numResults).pipe();    
  }  

  getNewsForStatus(_statusField,_status): Observable<RestResponse> {
      return this.getNewsForStatusStarting(_statusField,_status,0);    
  }
  getNewsForStatusStarting(_statusField,_status,starting): Observable<RestResponse> {
      return this.getNewsForStatusStartingInRange(_statusField,_status,starting,25);    
  }  
  getNewsForStatusStartingInRange(_statusField,_status,starting,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/status/'+_statusField + '/' +_status+ '/'+starting+'/'+numResults).pipe();    
  }  

    
  fetchNewsForCompany(id): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/news/symbol/'+id+'/fetch',null,{}).pipe(); 
  }
  
  updateUserRating(id,userrating): Observable<RestResponse> {
    var newsItem:NewsItemForm = new NewsItemForm();
    newsItem.id=id;
    newsItem.rating=userrating;
    return this.http.post<RestResponse>(this.apiRoot+'/news/userrating',newsItem,{}).pipe();    

  }
  updateSystemRating(id,userrating): Observable<RestResponse> {
    var newsItem:NewsItemForm = new NewsItemForm();
    newsItem.id=id;
    newsItem.rating=userrating;
    return this.http.post<RestResponse>(this.apiRoot+'/news/systemrating',newsItem,{}).pipe();    

  }

  
}
