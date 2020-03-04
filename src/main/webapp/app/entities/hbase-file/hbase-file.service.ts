import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHbaseFile } from 'app/shared/model/hbase-file.model';

type EntityResponseType = HttpResponse<IHbaseFile>;
type EntityArrayResponseType = HttpResponse<IHbaseFile[]>;

@Injectable({ providedIn: 'root' })
export class HbaseFileService {
  public resourceUrl = SERVER_API_URL + 'api/hbase-files';

  constructor(protected http: HttpClient) {}

  create(hbaseFile: IHbaseFile): Observable<EntityResponseType> {
    return this.http.post<IHbaseFile>(this.resourceUrl, hbaseFile, { observe: 'response' });
  }

  update(hbaseFile: IHbaseFile): Observable<EntityResponseType> {
    return this.http.put<IHbaseFile>(this.resourceUrl, hbaseFile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHbaseFile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHbaseFile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
