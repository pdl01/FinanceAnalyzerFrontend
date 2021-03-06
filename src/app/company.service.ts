import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 
import {environment} from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class CompanyService {

 
  constructor( private http: HttpClient) { }
 private apiRoot = environment.serverUrl+'/api/v1';

  getCompanyById(id): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/company/'+id).pipe();    
  }  
  searchCompaniesBySymbol(searchTerm,start,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/symbol/'+searchTerm).pipe();    
  }
  searchCompaniesByName(searchTerm,start,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/name/'+searchTerm).pipe();    
  }
  searchCompaniesByIndustry(searchTerm,start,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/industry/'+encodeURIComponent(searchTerm)).pipe();    
  }
  searchCompaniesBySector(searchTerm,start,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/sector/'+encodeURIComponent(searchTerm)).pipe();    
  }
  getStockHistory(id): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/company/'+id+'/stock').pipe();    
  }
  getSystemActivity(id): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/company/'+id+'/systemActivity').pipe();    
  }

  fetchStockData(symbol): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/companies/symbol/'+symbol+'/stock/fetch',null,{}).pipe();    
  }  
  getSectorNames(): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/sectorNames').pipe();    
  }
  getIndustryNames(): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/industryNames').pipe();    
  }
  getStockPerformance(id): Observable<RestResponse> {
    return this.getStockPerformanceStarting(id,0);    
  }
  getStockPerformanceStarting(id,starting): Observable<RestResponse> {
    return this.getStockPerformanceStartingInRange(id,starting,20);    
  }
  getStockPerformanceStartingInRange(id,starting,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/company/'+id+'/stockperformance/'+starting+'/'+numResults).pipe();    
  }
  triggerStockDataFetch(): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/companies/stockhistory/fetchLatestData',null,{}).pipe();    
  }
  triggerStockDataFetchForDate(date): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/companies/stockhistory/fetchLatestData/'+date,null,{}).pipe();    
  }
  updateCompanyDownloadOptions(company): Observable<RestResponse> {
    return this.http.post<RestResponse>(this.apiRoot+'/companies/company/'+company.id+'/downloadoptions',company,{}).pipe();    
  }  
}
