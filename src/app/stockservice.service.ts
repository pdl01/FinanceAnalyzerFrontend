import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RestResponse } from './restresponse'; 


@Injectable({
  providedIn: 'root'
})
export class StockserviceService {
  private apiRoot = 'http://localhost:8080/api/v1';

  constructor( private http: HttpClient) { }

  searchCompanies(searchTerm,start,numResults): Observable<RestResponse> {
    return this.http.get<RestResponse>(this.apiRoot+'/companies/symbol/'+searchTerm).pipe(
      catchError(this.handleError<RestResponse>('searchCompanies', null))
    );    
  }
}
