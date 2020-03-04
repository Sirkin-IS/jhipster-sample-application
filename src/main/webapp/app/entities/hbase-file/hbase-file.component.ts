import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHbaseFile } from 'app/shared/model/hbase-file.model';
import { HbaseFileService } from './hbase-file.service';
import { HbaseFileDeleteDialogComponent } from './hbase-file-delete-dialog.component';

@Component({
  selector: 'jhi-hbase-file',
  templateUrl: './hbase-file.component.html'
})
export class HbaseFileComponent implements OnInit, OnDestroy {
  hbaseFiles?: IHbaseFile[];
  eventSubscriber?: Subscription;

  constructor(protected hbaseFileService: HbaseFileService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.hbaseFileService.query().subscribe((res: HttpResponse<IHbaseFile[]>) => (this.hbaseFiles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHbaseFiles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHbaseFile): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHbaseFiles(): void {
    this.eventSubscriber = this.eventManager.subscribe('hbaseFileListModification', () => this.loadAll());
  }

  delete(hbaseFile: IHbaseFile): void {
    const modalRef = this.modalService.open(HbaseFileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hbaseFile = hbaseFile;
  }
}
