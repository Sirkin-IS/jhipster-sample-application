import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';

type EntityResponseType = HttpResponse<ITransportPackageRepeat>;
type EntityArrayResponseType = HttpResponse<ITransportPackageRepeat[]>;

@Injectable({ providedIn: 'root' })
export class TransportPackageRepeatService {
  public resourceUrl = SERVER_API_URL + 'api/transport-package-repeats';

  constructor(protected http: HttpClient) {}

  create(transportPackageRepeat: ITransportPackageRepeat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transportPackageRepeat);
    return this.http
      .post<ITransportPackageRepeat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transportPackageRepeat: ITransportPackageRepeat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transportPackageRepeat);
    return this.http
      .put<ITransportPackageRepeat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransportPackageRepeat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransportPackageRepeat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transportPackageRepeat: ITransportPackageRepeat): ITransportPackageRepeat {
    const copy: ITransportPackageRepeat = Object.assign({}, transportPackageRepeat, {
      createdAt:
        transportPackageRepeat.createdAt && transportPackageRepeat.createdAt.isValid()
          ? transportPackageRepeat.createdAt.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transportPackageRepeat: ITransportPackageRepeat) => {
        transportPackageRepeat.createdAt = transportPackageRepeat.createdAt ? moment(transportPackageRepeat.createdAt) : undefined;
      });
    }
    return res;
  }
}
