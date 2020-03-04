import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMessageType } from 'app/shared/model/message-type.model';
import { MessageTypeService } from './message-type.service';

@Component({
  templateUrl: './message-type-delete-dialog.component.html'
})
export class MessageTypeDeleteDialogComponent {
  messageType?: IMessageType;

  constructor(
    protected messageTypeService: MessageTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.messageTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('messageTypeListModification');
      this.activeModal.close();
    });
  }
}
