import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReceipt } from 'app/shared/model/receipt.model';

type EntityResponseType = HttpResponse<IReceipt>;
type EntityArrayResponseType = HttpResponse<IReceipt[]>;

@Injectable({ providedIn: 'root' })
export class ReceiptService {
  public resourceUrl = SERVER_API_URL + 'api/receipts';

  constructor(protected http: HttpClient) {}

  create(receipt: IReceipt): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receipt);
    return this.http
      .post<IReceipt>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(receipt: IReceipt): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receipt);
    return this.http
      .put<IReceipt>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReceipt>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReceipt[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(receipt: IReceipt): IReceipt {
    const copy: IReceipt = Object.assign({}, receipt, {
      receiptDate: receipt.receiptDate && receipt.receiptDate.isValid() ? receipt.receiptDate.format(DATE_FORMAT) : undefined,
      createdAt: receipt.createdAt && receipt.createdAt.isValid() ? receipt.createdAt.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.receiptDate = res.body.receiptDate ? moment(res.body.receiptDate) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((receipt: IReceipt) => {
        receipt.receiptDate = receipt.receiptDate ? moment(receipt.receiptDate) : undefined;
        receipt.createdAt = receipt.createdAt ? moment(receipt.createdAt) : undefined;
      });
    }
    return res;
  }
}
