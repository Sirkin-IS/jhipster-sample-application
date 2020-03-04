import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';
import { TransportPackageRepeatService } from './transport-package-repeat.service';

@Component({
  templateUrl: './transport-package-repeat-delete-dialog.component.html'
})
export class TransportPackageRepeatDeleteDialogComponent {
  transportPackageRepeat?: ITransportPackageRepeat;

  constructor(
    protected transportPackageRepeatService: TransportPackageRepeatService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transportPackageRepeatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transportPackageRepeatListModification');
      this.activeModal.close();
    });
  }
}
