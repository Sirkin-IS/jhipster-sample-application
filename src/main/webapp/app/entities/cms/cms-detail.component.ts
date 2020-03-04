import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICMS } from 'app/shared/model/cms.model';

@Component({
  selector: 'jhi-cms-detail',
  templateUrl: './cms-detail.component.html'
})
export class CMSDetailComponent implements OnInit {
  cMS: ICMS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cMS }) => (this.cMS = cMS));
  }

  previousState(): void {
    window.history.back();
  }
}
