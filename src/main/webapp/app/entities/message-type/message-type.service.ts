import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMessageType } from 'app/shared/model/message-type.model';

type EntityResponseType = HttpResponse<IMessageType>;
type EntityArrayResponseType = HttpResponse<IMessageType[]>;

@Injectable({ providedIn: 'root' })
export class MessageTypeService {
  public resourceUrl = SERVER_API_URL + 'api/message-types';

  constructor(protected http: HttpClient) {}

  create(messageType: IMessageType): Observable<EntityResponseType> {
    return this.http.post<IMessageType>(this.resourceUrl, messageType, { observe: 'response' });
  }

  update(messageType: IMessageType): Observable<EntityResponseType> {
    return this.http.put<IMessageType>(this.resourceUrl, messageType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMessageType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMessageType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
