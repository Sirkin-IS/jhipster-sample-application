import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentStatus } from 'app/shared/model/document-status.model';
import { DocumentStatusService } from './document-status.service';
import { DocumentStatusDeleteDialogComponent } from './document-status-delete-dialog.component';

@Component({
  selector: 'jhi-document-status',
  templateUrl: './document-status.component.html'
})
export class DocumentStatusComponent implements OnInit, OnDestroy {
  documentStatuses?: IDocumentStatus[];
  eventSubscriber?: Subscription;

  constructor(
    protected documentStatusService: DocumentStatusService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.documentStatusService.query().subscribe((res: HttpResponse<IDocumentStatus[]>) => (this.documentStatuses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDocumentStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDocumentStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDocumentStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('documentStatusListModification', () => this.loadAll());
  }

  delete(documentStatus: IDocumentStatus): void {
    const modalRef = this.modalService.open(DocumentStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.documentStatus = documentStatus;
  }
}
