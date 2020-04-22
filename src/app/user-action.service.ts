import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 
import {environment} from '../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class UserActionService {

  constructor( private http: HttpClient) { }
 private apiRoot = environment.serverUrl+'/api/v1';
   getSearchHistory(): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/useractions/recent-searches').pipe();    
  } 
}
