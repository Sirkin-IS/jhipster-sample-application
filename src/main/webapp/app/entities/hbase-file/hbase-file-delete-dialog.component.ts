import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHbaseFile } from 'app/shared/model/hbase-file.model';
import { HbaseFileService } from './hbase-file.service';

@Component({
  templateUrl: './hbase-file-delete-dialog.component.html'
})
export class HbaseFileDeleteDialogComponent {
  hbaseFile?: IHbaseFile;

  constructor(protected hbaseFileService: HbaseFileService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hbaseFileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hbaseFileListModification');
      this.activeModal.close();
    });
  }
}
