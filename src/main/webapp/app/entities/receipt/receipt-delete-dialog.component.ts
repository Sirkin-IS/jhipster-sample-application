import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReceipt } from 'app/shared/model/receipt.model';
import { ReceiptService } from './receipt.service';

@Component({
  templateUrl: './receipt-delete-dialog.component.html'
})
export class ReceiptDeleteDialogComponent {
  receipt?: IReceipt;

  constructor(protected receiptService: ReceiptService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.receiptService.delete(id).subscribe(() => {
      this.eventManager.broadcast('receiptListModification');
      this.activeModal.close();
    });
  }
}
