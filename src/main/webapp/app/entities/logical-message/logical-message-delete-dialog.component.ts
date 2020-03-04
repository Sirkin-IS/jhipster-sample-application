import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogicalMessage } from 'app/shared/model/logical-message.model';
import { LogicalMessageService } from './logical-message.service';

@Component({
  templateUrl: './logical-message-delete-dialog.component.html'
})
export class LogicalMessageDeleteDialogComponent {
  logicalMessage?: ILogicalMessage;

  constructor(
    protected logicalMessageService: LogicalMessageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logicalMessageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('logicalMessageListModification');
      this.activeModal.close();
    });
  }
}
