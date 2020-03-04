import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHbaseFile, HbaseFile } from 'app/shared/model/hbase-file.model';
import { HbaseFileService } from './hbase-file.service';
import { HbaseFileComponent } from './hbase-file.component';
import { HbaseFileDetailComponent } from './hbase-file-detail.component';
import { HbaseFileUpdateComponent } from './hbase-file-update.component';

@Injectable({ providedIn: 'root' })
export class HbaseFileResolve implements Resolve<IHbaseFile> {
  constructor(private service: HbaseFileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHbaseFile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hbaseFile: HttpResponse<HbaseFile>) => {
          if (hbaseFile.body) {
            return of(hbaseFile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HbaseFile());
  }
}

export const hbaseFileRoute: Routes = [
  {
    path: '',
    component: HbaseFileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'HbaseFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HbaseFileDetailComponent,
    resolve: {
      hbaseFile: HbaseFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'HbaseFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HbaseFileUpdateComponent,
    resolve: {
      hbaseFile: HbaseFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'HbaseFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HbaseFileUpdateComponent,
    resolve: {
      hbaseFile: HbaseFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'HbaseFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];
