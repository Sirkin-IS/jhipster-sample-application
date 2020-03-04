import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransportPackageRepeat, TransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';
import { TransportPackageRepeatService } from './transport-package-repeat.service';
import { TransportPackageRepeatComponent } from './transport-package-repeat.component';
import { TransportPackageRepeatDetailComponent } from './transport-package-repeat-detail.component';
import { TransportPackageRepeatUpdateComponent } from './transport-package-repeat-update.component';

@Injectable({ providedIn: 'root' })
export class TransportPackageRepeatResolve implements Resolve<ITransportPackageRepeat> {
  constructor(private service: TransportPackageRepeatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransportPackageRepeat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transportPackageRepeat: HttpResponse<TransportPackageRepeat>) => {
          if (transportPackageRepeat.body) {
            return of(transportPackageRepeat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransportPackageRepeat());
  }
}

export const transportPackageRepeatRoute: Routes = [
  {
    path: '',
    component: TransportPackageRepeatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransportPackageRepeats'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransportPackageRepeatDetailComponent,
    resolve: {
      transportPackageRepeat: TransportPackageRepeatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransportPackageRepeats'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransportPackageRepeatUpdateComponent,
    resolve: {
      transportPackageRepeat: TransportPackageRepeatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransportPackageRepeats'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransportPackageRepeatUpdateComponent,
    resolve: {
      transportPackageRepeat: TransportPackageRepeatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransportPackageRepeats'
    },
    canActivate: [UserRouteAccessService]
  }
];
