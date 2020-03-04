import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILogicalMessage, LogicalMessage } from 'app/shared/model/logical-message.model';
import { LogicalMessageService } from './logical-message.service';
import { LogicalMessageComponent } from './logical-message.component';
import { LogicalMessageDetailComponent } from './logical-message-detail.component';
import { LogicalMessageUpdateComponent } from './logical-message-update.component';

@Injectable({ providedIn: 'root' })
export class LogicalMessageResolve implements Resolve<ILogicalMessage> {
  constructor(private service: LogicalMessageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILogicalMessage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((logicalMessage: HttpResponse<LogicalMessage>) => {
          if (logicalMessage.body) {
            return of(logicalMessage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LogicalMessage());
  }
}

export const logicalMessageRoute: Routes = [
  {
    path: '',
    component: LogicalMessageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LogicalMessages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LogicalMessageDetailComponent,
    resolve: {
      logicalMessage: LogicalMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LogicalMessages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LogicalMessageUpdateComponent,
    resolve: {
      logicalMessage: LogicalMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LogicalMessages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LogicalMessageUpdateComponent,
    resolve: {
      logicalMessage: LogicalMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LogicalMessages'
    },
    canActivate: [UserRouteAccessService]
  }
];
