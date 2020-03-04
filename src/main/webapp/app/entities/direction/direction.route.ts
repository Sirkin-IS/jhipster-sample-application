import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDirection, Direction } from 'app/shared/model/direction.model';
import { DirectionService } from './direction.service';
import { DirectionComponent } from './direction.component';
import { DirectionDetailComponent } from './direction-detail.component';
import { DirectionUpdateComponent } from './direction-update.component';

@Injectable({ providedIn: 'root' })
export class DirectionResolve implements Resolve<IDirection> {
  constructor(private service: DirectionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDirection> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((direction: HttpResponse<Direction>) => {
          if (direction.body) {
            return of(direction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Direction());
  }
}

export const directionRoute: Routes = [
  {
    path: '',
    component: DirectionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Directions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DirectionDetailComponent,
    resolve: {
      direction: DirectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Directions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DirectionUpdateComponent,
    resolve: {
      direction: DirectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Directions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DirectionUpdateComponent,
    resolve: {
      direction: DirectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Directions'
    },
    canActivate: [UserRouteAccessService]
  }
];
