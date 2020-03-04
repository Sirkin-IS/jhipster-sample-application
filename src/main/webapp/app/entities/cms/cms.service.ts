import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICMS } from 'app/shared/model/cms.model';

type EntityResponseType = HttpResponse<ICMS>;
type EntityArrayResponseType = HttpResponse<ICMS[]>;

@Injectable({ providedIn: 'root' })
export class CMSService {
  public resourceUrl = SERVER_API_URL + 'api/cms';

  constructor(protected http: HttpClient) {}

  create(cMS: ICMS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cMS);
    return this.http
      .post<ICMS>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cMS: ICMS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cMS);
    return this.http
      .put<ICMS>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICMS>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICMS[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cMS: ICMS): ICMS {
    const copy: ICMS = Object.assign({}, cMS, {
      createdAt: cMS.createdAt && cMS.createdAt.isValid() ? cMS.createdAt.format(DATE_FORMAT) : undefined
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
      res.body.forEach((cMS: ICMS) => {
        cMS.createdAt = cMS.createdAt ? moment(cMS.createdAt) : undefined;
      });
    }
    return res;
  }
}
