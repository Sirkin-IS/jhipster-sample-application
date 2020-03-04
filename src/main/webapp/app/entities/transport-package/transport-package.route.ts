import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransportPackage, TransportPackage } from 'app/shared/model/transport-package.model';
import { TransportPackageService } from './transport-package.service';
import { TransportPackageComponent } from './transport-package.component';
import { TransportPackageDetailComponent } from './transport-package-detail.component';
import { TransportPackageUpdateComponent } from './transport-package-update.component';

@Injectable({ providedIn: 'root' })
export class TransportPackageResolve implements Resolve<ITransportPackage> {
  constructor(private service: TransportPackageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransportPackage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transportPackage: HttpResponse<TransportPackage>) => {
          if (transportPackage.body) {
            return of(transportPackage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransportPackage());
  }
}

export const transportPackageRoute: Routes = [
  {
    path: '',
    component: TransportPackageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransportPackages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransportPackageDetailComponent,
    resolve: {
      transportPackage: TransportPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransportPackages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransportPackageUpdateComponent,
    resolve: {
      transportPackage: TransportPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransportPackages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransportPackageUpdateComponent,
    resolve: {
      transportPackage: TransportPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransportPackages'
    },
    canActivate: [UserRouteAccessService]
  }
];
