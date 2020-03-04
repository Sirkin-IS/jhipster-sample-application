import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICMS, CMS } from 'app/shared/model/cms.model';
import { CMSService } from './cms.service';
import { CMSComponent } from './cms.component';
import { CMSDetailComponent } from './cms-detail.component';
import { CMSUpdateComponent } from './cms-update.component';

@Injectable({ providedIn: 'root' })
export class CMSResolve implements Resolve<ICMS> {
  constructor(private service: CMSService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICMS> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cMS: HttpResponse<CMS>) => {
          if (cMS.body) {
            return of(cMS.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CMS());
  }
}

export const cMSRoute: Routes = [
  {
    path: '',
    component: CMSComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CMS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CMSDetailComponent,
    resolve: {
      cMS: CMSResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CMS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CMSUpdateComponent,
    resolve: {
      cMS: CMSResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CMS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CMSUpdateComponent,
    resolve: {
      cMS: CMSResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CMS'
    },
    canActivate: [UserRouteAccessService]
  }
];
