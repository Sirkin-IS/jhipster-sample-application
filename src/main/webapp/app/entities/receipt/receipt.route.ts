import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReceipt, Receipt } from 'app/shared/model/receipt.model';
import { ReceiptService } from './receipt.service';
import { ReceiptComponent } from './receipt.component';
import { ReceiptDetailComponent } from './receipt-detail.component';
import { ReceiptUpdateComponent } from './receipt-update.component';

@Injectable({ providedIn: 'root' })
export class ReceiptResolve implements Resolve<IReceipt> {
  constructor(private service: ReceiptService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceipt> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((receipt: HttpResponse<Receipt>) => {
          if (receipt.body) {
            return of(receipt.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Receipt());
  }
}

export const receiptRoute: Routes = [
  {
    path: '',
    component: ReceiptComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Receipts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReceiptDetailComponent,
    resolve: {
      receipt: ReceiptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Receipts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReceiptUpdateComponent,
    resolve: {
      receipt: ReceiptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Receipts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReceiptUpdateComponent,
    resolve: {
      receipt: ReceiptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Receipts'
    },
    canActivate: [UserRouteAccessService]
  }
];
