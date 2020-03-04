import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransportPackage } from 'app/shared/model/transport-package.model';
import { TransportPackageService } from './transport-package.service';

@Component({
  templateUrl: './transport-package-delete-dialog.component.html'
})
export class TransportPackageDeleteDialogComponent {
  transportPackage?: ITransportPackage;

  constructor(
    protected transportPackageService: TransportPackageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transportPackageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transportPackageListModification');
      this.activeModal.close();
    });
  }
}
