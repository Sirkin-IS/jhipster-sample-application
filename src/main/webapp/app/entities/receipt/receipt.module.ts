import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { ReceiptComponent } from './receipt.component';
import { ReceiptDetailComponent } from './receipt-detail.component';
import { ReceiptUpdateComponent } from './receipt-update.component';
import { ReceiptDeleteDialogComponent } from './receipt-delete-dialog.component';
import { receiptRoute } from './receipt.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(receiptRoute)],
  declarations: [ReceiptComponent, ReceiptDetailComponent, ReceiptUpdateComponent, ReceiptDeleteDialogComponent],
  entryComponents: [ReceiptDeleteDialogComponent]
})
export class JhipsterSampleApplicationReceiptModule {}
