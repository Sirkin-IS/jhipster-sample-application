import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';

type EntityResponseType = HttpResponse<IBadIncomingTransportPackage>;
type EntityArrayResponseType = HttpResponse<IBadIncomingTransportPackage[]>;

@Injectable({ providedIn: 'root' })
export class BadIncomingTransportPackageService {
  public resourceUrl = SERVER_API_URL + 'api/bad-incoming-transport-packages';

  constructor(protected http: HttpClient) {}

  create(badIncomingTransportPackage: IBadIncomingTransportPackage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(badIncomingTransportPackage);
    return this.http
      .post<IBadIncomingTransportPackage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(badIncomingTransportPackage: IBadIncomingTransportPackage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(badIncomingTransportPackage);
    return this.http
      .put<IBadIncomingTransportPackage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBadIncomingTransportPackage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBadIncomingTransportPackage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(badIncomingTransportPackage: IBadIncomingTransportPackage): IBadIncomingTransportPackage {
    const copy: IBadIncomingTransportPackage = Object.assign({}, badIncomingTransportPackage, {
      date:
        badIncomingTransportPackage.date && badIncomingTransportPackage.date.isValid()
          ? badIncomingTransportPackage.date.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((badIncomingTransportPackage: IBadIncomingTransportPackage) => {
        badIncomingTransportPackage.date = badIncomingTransportPackage.date ? moment(badIncomingTransportPackage.date) : undefined;
      });
    }
    return res;
  }
}
