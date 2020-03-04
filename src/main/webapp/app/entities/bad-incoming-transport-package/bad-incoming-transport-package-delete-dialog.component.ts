import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';
import { BadIncomingTransportPackageService } from './bad-incoming-transport-package.service';

@Component({
  templateUrl: './bad-incoming-transport-package-delete-dialog.component.html'
})
export class BadIncomingTransportPackageDeleteDialogComponent {
  badIncomingTransportPackage?: IBadIncomingTransportPackage;

  constructor(
    protected badIncomingTransportPackageService: BadIncomingTransportPackageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.badIncomingTransportPackageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('badIncomingTransportPackageListModification');
      this.activeModal.close();
    });
  }
}
