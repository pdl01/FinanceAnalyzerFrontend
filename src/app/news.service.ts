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
   getLatestNews(): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/news/latest').pipe();    
  }  
}
