import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperators } from 'app/shared/model/operators.model';
import { OperatorsService } from './operators.service';

@Component({
  templateUrl: './operators-delete-dialog.component.html'
})
export class OperatorsDeleteDialogComponent {
  operators?: IOperators;

  constructor(protected operatorsService: OperatorsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.operatorsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('operatorsListModification');
      this.activeModal.close();
    });
  }
}
