import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransportPackage } from 'app/shared/model/transport-package.model';
import { TransportPackageService } from './transport-package.service';
import { TransportPackageDeleteDialogComponent } from './transport-package-delete-dialog.component';

@Component({
  selector: 'jhi-transport-package',
  templateUrl: './transport-package.component.html'
})
export class TransportPackageComponent implements OnInit, OnDestroy {
  transportPackages?: ITransportPackage[];
  eventSubscriber?: Subscription;

  constructor(
    protected transportPackageService: TransportPackageService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.transportPackageService.query().subscribe((res: HttpResponse<ITransportPackage[]>) => (this.transportPackages = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransportPackages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransportPackage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransportPackages(): void {
    this.eventSubscriber = this.eventManager.subscribe('transportPackageListModification', () => this.loadAll());
  }

  delete(transportPackage: ITransportPackage): void {
    const modalRef = this.modalService.open(TransportPackageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transportPackage = transportPackage;
  }
}
