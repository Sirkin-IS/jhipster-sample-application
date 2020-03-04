import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICMS } from 'app/shared/model/cms.model';
import { CMSService } from './cms.service';

@Component({
  templateUrl: './cms-delete-dialog.component.html'
})
export class CMSDeleteDialogComponent {
  cMS?: ICMS;

  constructor(protected cMSService: CMSService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cMSService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cMSListModification');
      this.activeModal.close();
    });
  }
}
