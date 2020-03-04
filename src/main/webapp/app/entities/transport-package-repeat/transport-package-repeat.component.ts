import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';
import { TransportPackageRepeatService } from './transport-package-repeat.service';
import { TransportPackageRepeatDeleteDialogComponent } from './transport-package-repeat-delete-dialog.component';

@Component({
  selector: 'jhi-transport-package-repeat',
  templateUrl: './transport-package-repeat.component.html'
})
export class TransportPackageRepeatComponent implements OnInit, OnDestroy {
  transportPackageRepeats?: ITransportPackageRepeat[];
  eventSubscriber?: Subscription;

  constructor(
    protected transportPackageRepeatService: TransportPackageRepeatService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.transportPackageRepeatService
      .query()
      .subscribe((res: HttpResponse<ITransportPackageRepeat[]>) => (this.transportPackageRepeats = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransportPackageRepeats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransportPackageRepeat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransportPackageRepeats(): void {
    this.eventSubscriber = this.eventManager.subscribe('transportPackageRepeatListModification', () => this.loadAll());
  }

  delete(transportPackageRepeat: ITransportPackageRepeat): void {
    const modalRef = this.modalService.open(TransportPackageRepeatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transportPackageRepeat = transportPackageRepeat;
  }
}
