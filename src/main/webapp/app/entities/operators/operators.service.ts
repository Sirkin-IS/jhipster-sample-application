import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOperators } from 'app/shared/model/operators.model';

type EntityResponseType = HttpResponse<IOperators>;
type EntityArrayResponseType = HttpResponse<IOperators[]>;

@Injectable({ providedIn: 'root' })
export class OperatorsService {
  public resourceUrl = SERVER_API_URL + 'api/operators';

  constructor(protected http: HttpClient) {}

  create(operators: IOperators): Observable<EntityResponseType> {
    return this.http.post<IOperators>(this.resourceUrl, operators, { observe: 'response' });
  }

  update(operators: IOperators): Observable<EntityResponseType> {
    return this.http.put<IOperators>(this.resourceUrl, operators, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOperators>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOperators[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
