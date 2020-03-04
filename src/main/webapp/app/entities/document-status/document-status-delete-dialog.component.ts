import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentStatus } from 'app/shared/model/document-status.model';
import { DocumentStatusService } from './document-status.service';

@Component({
  templateUrl: './document-status-delete-dialog.component.html'
})
export class DocumentStatusDeleteDialogComponent {
  documentStatus?: IDocumentStatus;

  constructor(
    protected documentStatusService: DocumentStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('documentStatusListModification');
      this.activeModal.close();
    });
  }
}
