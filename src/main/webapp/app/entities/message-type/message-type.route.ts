import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMessageType, MessageType } from 'app/shared/model/message-type.model';
import { MessageTypeService } from './message-type.service';
import { MessageTypeComponent } from './message-type.component';
import { MessageTypeDetailComponent } from './message-type-detail.component';
import { MessageTypeUpdateComponent } from './message-type-update.component';

@Injectable({ providedIn: 'root' })
export class MessageTypeResolve implements Resolve<IMessageType> {
  constructor(private service: MessageTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMessageType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((messageType: HttpResponse<MessageType>) => {
          if (messageType.body) {
            return of(messageType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MessageType());
  }
}

export const messageTypeRoute: Routes = [
  {
    path: '',
    component: MessageTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MessageTypeDetailComponent,
    resolve: {
      messageType: MessageTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MessageTypeUpdateComponent,
    resolve: {
      messageType: MessageTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MessageTypeUpdateComponent,
    resolve: {
      messageType: MessageTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
