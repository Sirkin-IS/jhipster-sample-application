import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILogicalMessage } from 'app/shared/model/logical-message.model';

type EntityResponseType = HttpResponse<ILogicalMessage>;
type EntityArrayResponseType = HttpResponse<ILogicalMessage[]>;

@Injectable({ providedIn: 'root' })
export class LogicalMessageService {
  public resourceUrl = SERVER_API_URL + 'api/logical-messages';

  constructor(protected http: HttpClient) {}

  create(logicalMessage: ILogicalMessage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logicalMessage);
    return this.http
      .post<ILogicalMessage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(logicalMessage: ILogicalMessage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logicalMessage);
    return this.http
      .put<ILogicalMessage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILogicalMessage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILogicalMessage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(logicalMessage: ILogicalMessage): ILogicalMessage {
    const copy: ILogicalMessage = Object.assign({}, logicalMessage, {
      lastTimeOfAttemps:
        logicalMessage.lastTimeOfAttemps && logicalMessage.lastTimeOfAttemps.isValid()
          ? logicalMessage.lastTimeOfAttemps.format(DATE_FORMAT)
          : undefined,
      createdAt: logicalMessage.createdAt && logicalMessage.createdAt.isValid() ? logicalMessage.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: logicalMessage.updatedAt && logicalMessage.updatedAt.isValid() ? logicalMessage.updatedAt.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastTimeOfAttemps = res.body.lastTimeOfAttemps ? moment(res.body.lastTimeOfAttemps) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((logicalMessage: ILogicalMessage) => {
        logicalMessage.lastTimeOfAttemps = logicalMessage.lastTimeOfAttemps ? moment(logicalMessage.lastTimeOfAttemps) : undefined;
        logicalMessage.createdAt = logicalMessage.createdAt ? moment(logicalMessage.createdAt) : undefined;
        logicalMessage.updatedAt = logicalMessage.updatedAt ? moment(logicalMessage.updatedAt) : undefined;
      });
    }
    return res;
  }
}
