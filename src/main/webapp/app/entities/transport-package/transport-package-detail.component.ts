import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransportPackage } from 'app/shared/model/transport-package.model';

@Component({
  selector: 'jhi-transport-package-detail',
  templateUrl: './transport-package-detail.component.html'
})
export class TransportPackageDetailComponent implements OnInit {
  transportPackage: ITransportPackage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transportPackage }) => (this.transportPackage = transportPackage));
  }

  previousState(): void {
    window.history.back();
  }
}
