import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

 
  constructor( private http: HttpClient) { }
 private apiRoot = 'http://localhost:8080/api/v1';

  getCompanyById(id): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/company/'+id).pipe();    
  }  
  searchCompanies(searchTerm,start,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/symbol/'+searchTerm).pipe();    
  }
  getStockHistory(id): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/company/'+id+'/stock').pipe();    
  }
  getSystemActivity(id): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/company/'+id+'/systemActivity').pipe();    
  }  
}
