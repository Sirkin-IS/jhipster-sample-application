import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDirection } from 'app/shared/model/direction.model';
import { DirectionService } from './direction.service';

@Component({
  templateUrl: './direction-delete-dialog.component.html'
})
export class DirectionDeleteDialogComponent {
  direction?: IDirection;

  constructor(protected directionService: DirectionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.directionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('directionListModification');
      this.activeModal.close();
    });
  }
}
