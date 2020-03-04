import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';

@Component({
  selector: 'jhi-transport-package-repeat-detail',
  templateUrl: './transport-package-repeat-detail.component.html'
})
export class TransportPackageRepeatDetailComponent implements OnInit {
  transportPackageRepeat: ITransportPackageRepeat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transportPackageRepeat }) => (this.transportPackageRepeat = transportPackageRepeat));
  }

  previousState(): void {
    window.history.back();
  }
}
