import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentHistory } from 'app/shared/model/document-history.model';
import { DocumentHistoryService } from './document-history.service';
import { DocumentHistoryDeleteDialogComponent } from './document-history-delete-dialog.component';

@Component({
  selector: 'jhi-document-history',
  templateUrl: './document-history.component.html'
})
export class DocumentHistoryComponent implements OnInit, OnDestroy {
  documentHistories?: IDocumentHistory[];
  eventSubscriber?: Subscription;

  constructor(
    protected documentHistoryService: DocumentHistoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.documentHistoryService.query().subscribe((res: HttpResponse<IDocumentHistory[]>) => (this.documentHistories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDocumentHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDocumentHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDocumentHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('documentHistoryListModification', () => this.loadAll());
  }

  delete(documentHistory: IDocumentHistory): void {
    const modalRef = this.modalService.open(DocumentHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.documentHistory = documentHistory;
  }
}
