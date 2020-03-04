import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBadIncomingTransportPackage, BadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';
import { BadIncomingTransportPackageService } from './bad-incoming-transport-package.service';
import { BadIncomingTransportPackageComponent } from './bad-incoming-transport-package.component';
import { BadIncomingTransportPackageDetailComponent } from './bad-incoming-transport-package-detail.component';
import { BadIncomingTransportPackageUpdateComponent } from './bad-incoming-transport-package-update.component';

@Injectable({ providedIn: 'root' })
export class BadIncomingTransportPackageResolve implements Resolve<IBadIncomingTransportPackage> {
  constructor(private service: BadIncomingTransportPackageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBadIncomingTransportPackage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((badIncomingTransportPackage: HttpResponse<BadIncomingTransportPackage>) => {
          if (badIncomingTransportPackage.body) {
            return of(badIncomingTransportPackage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BadIncomingTransportPackage());
  }
}

export const badIncomingTransportPackageRoute: Routes = [
  {
    path: '',
    component: BadIncomingTransportPackageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BadIncomingTransportPackages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BadIncomingTransportPackageDetailComponent,
    resolve: {
      badIncomingTransportPackage: BadIncomingTransportPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BadIncomingTransportPackages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BadIncomingTransportPackageUpdateComponent,
    resolve: {
      badIncomingTransportPackage: BadIncomingTransportPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BadIncomingTransportPackages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BadIncomingTransportPackageUpdateComponent,
    resolve: {
      badIncomingTransportPackage: BadIncomingTransportPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BadIncomingTransportPackages'
    },
    canActivate: [UserRouteAccessService]
  }
];
