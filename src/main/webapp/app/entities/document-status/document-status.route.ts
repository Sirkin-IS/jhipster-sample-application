import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocumentStatus, DocumentStatus } from 'app/shared/model/document-status.model';
import { DocumentStatusService } from './document-status.service';
import { DocumentStatusComponent } from './document-status.component';
import { DocumentStatusDetailComponent } from './document-status-detail.component';
import { DocumentStatusUpdateComponent } from './document-status-update.component';

@Injectable({ providedIn: 'root' })
export class DocumentStatusResolve implements Resolve<IDocumentStatus> {
  constructor(private service: DocumentStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((documentStatus: HttpResponse<DocumentStatus>) => {
          if (documentStatus.body) {
            return of(documentStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentStatus());
  }
}

export const documentStatusRoute: Routes = [
  {
    path: '',
    component: DocumentStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocumentStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocumentStatusDetailComponent,
    resolve: {
      documentStatus: DocumentStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocumentStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocumentStatusUpdateComponent,
    resolve: {
      documentStatus: DocumentStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocumentStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocumentStatusUpdateComponent,
    resolve: {
      documentStatus: DocumentStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocumentStatuses'
    },
    canActivate: [UserRouteAccessService]
  }
];
