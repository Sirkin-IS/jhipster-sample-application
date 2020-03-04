import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransportPackage } from 'app/shared/model/transport-package.model';

type EntityResponseType = HttpResponse<ITransportPackage>;
type EntityArrayResponseType = HttpResponse<ITransportPackage[]>;

@Injectable({ providedIn: 'root' })
export class TransportPackageService {
  public resourceUrl = SERVER_API_URL + 'api/transport-packages';

  constructor(protected http: HttpClient) {}

  create(transportPackage: ITransportPackage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transportPackage);
    return this.http
      .post<ITransportPackage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transportPackage: ITransportPackage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transportPackage);
    return this.http
      .put<ITransportPackage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransportPackage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransportPackage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transportPackage: ITransportPackage): ITransportPackage {
    const copy: ITransportPackage = Object.assign({}, transportPackage, {
      lastTimeOfAttemps:
        transportPackage.lastTimeOfAttemps && transportPackage.lastTimeOfAttemps.isValid()
          ? transportPackage.lastTimeOfAttemps.format(DATE_FORMAT)
          : undefined,
      createdAt:
        transportPackage.createdAt && transportPackage.createdAt.isValid() ? transportPackage.createdAt.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastTimeOfAttemps = res.body.lastTimeOfAttemps ? moment(res.body.lastTimeOfAttemps) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transportPackage: ITransportPackage) => {
        transportPackage.lastTimeOfAttemps = transportPackage.lastTimeOfAttemps ? moment(transportPackage.lastTimeOfAttemps) : undefined;
        transportPackage.createdAt = transportPackage.createdAt ? moment(transportPackage.createdAt) : undefined;
      });
    }
    return res;
  }
}
