import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';
import { BadIncomingTransportPackageService } from './bad-incoming-transport-package.service';
import { BadIncomingTransportPackageDeleteDialogComponent } from './bad-incoming-transport-package-delete-dialog.component';

@Component({
  selector: 'jhi-bad-incoming-transport-package',
  templateUrl: './bad-incoming-transport-package.component.html'
})
export class BadIncomingTransportPackageComponent implements OnInit, OnDestroy {
  badIncomingTransportPackages?: IBadIncomingTransportPackage[];
  eventSubscriber?: Subscription;

  constructor(
    protected badIncomingTransportPackageService: BadIncomingTransportPackageService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.badIncomingTransportPackageService
      .query()
      .subscribe((res: HttpResponse<IBadIncomingTransportPackage[]>) => (this.badIncomingTransportPackages = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBadIncomingTransportPackages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBadIncomingTransportPackage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInBadIncomingTransportPackages(): void {
    this.eventSubscriber = this.eventManager.subscribe('badIncomingTransportPackageListModification', () => this.loadAll());
  }

  delete(badIncomingTransportPackage: IBadIncomingTransportPackage): void {
    const modalRef = this.modalService.open(BadIncomingTransportPackageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.badIncomingTransportPackage = badIncomingTransportPackage;
  }
}
