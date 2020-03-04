import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceipt } from 'app/shared/model/receipt.model';
import { ReceiptService } from './receipt.service';
import { ReceiptDeleteDialogComponent } from './receipt-delete-dialog.component';

@Component({
  selector: 'jhi-receipt',
  templateUrl: './receipt.component.html'
})
export class ReceiptComponent implements OnInit, OnDestroy {
  receipts?: IReceipt[];
  eventSubscriber?: Subscription;

  constructor(protected receiptService: ReceiptService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.receiptService.query().subscribe((res: HttpResponse<IReceipt[]>) => (this.receipts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReceipts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReceipt): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReceipts(): void {
    this.eventSubscriber = this.eventManager.subscribe('receiptListModification', () => this.loadAll());
  }

  delete(receipt: IReceipt): void {
    const modalRef = this.modalService.open(ReceiptDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.receipt = receipt;
  }
}
