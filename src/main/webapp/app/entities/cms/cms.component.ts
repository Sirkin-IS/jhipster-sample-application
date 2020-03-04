import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICMS } from 'app/shared/model/cms.model';
import { CMSService } from './cms.service';
import { CMSDeleteDialogComponent } from './cms-delete-dialog.component';

@Component({
  selector: 'jhi-cms',
  templateUrl: './cms.component.html'
})
export class CMSComponent implements OnInit, OnDestroy {
  cMS?: ICMS[];
  eventSubscriber?: Subscription;

  constructor(protected cMSService: CMSService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.cMSService.query().subscribe((res: HttpResponse<ICMS[]>) => (this.cMS = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCMS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICMS): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCMS(): void {
    this.eventSubscriber = this.eventManager.subscribe('cMSListModification', () => this.loadAll());
  }

  delete(cMS: ICMS): void {
    const modalRef = this.modalService.open(CMSDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cMS = cMS;
  }
}
