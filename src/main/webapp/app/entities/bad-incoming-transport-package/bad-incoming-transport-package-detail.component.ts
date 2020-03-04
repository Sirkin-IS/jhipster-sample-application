import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';

@Component({
  selector: 'jhi-bad-incoming-transport-package-detail',
  templateUrl: './bad-incoming-transport-package-detail.component.html'
})
export class BadIncomingTransportPackageDetailComponent implements OnInit {
  badIncomingTransportPackage: IBadIncomingTransportPackage | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ badIncomingTransportPackage }) => (this.badIncomingTransportPackage = badIncomingTransportPackage)
    );
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
