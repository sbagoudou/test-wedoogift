import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CalculatorResult } from '../../models/calculator-result';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WedoogiftApiService {

  constructor(private httpClient: HttpClient) {}

  getCombination$(shopId: number, amount: number): Observable<CalculatorResult> {
    return this.httpClient.get<CalculatorResult>(`${environment.baseURL}/shop/${shopId}/search-combination?amount=${amount}`);
  }
}
