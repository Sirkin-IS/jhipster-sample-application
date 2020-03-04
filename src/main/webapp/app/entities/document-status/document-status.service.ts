import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDocumentStatus } from 'app/shared/model/document-status.model';

type EntityResponseType = HttpResponse<IDocumentStatus>;
type EntityArrayResponseType = HttpResponse<IDocumentStatus[]>;

@Injectable({ providedIn: 'root' })
export class DocumentStatusService {
  public resourceUrl = SERVER_API_URL + 'api/document-statuses';

  constructor(protected http: HttpClient) {}

  create(documentStatus: IDocumentStatus): Observable<EntityResponseType> {
    return this.http.post<IDocumentStatus>(this.resourceUrl, documentStatus, { observe: 'response' });
  }

  update(documentStatus: IDocumentStatus): Observable<EntityResponseType> {
    return this.http.put<IDocumentStatus>(this.resourceUrl, documentStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocumentStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumentStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
