import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperators } from 'app/shared/model/operators.model';

@Component({
  selector: 'jhi-operators-detail',
  templateUrl: './operators-detail.component.html'
})
export class OperatorsDetailComponent implements OnInit {
  operators: IOperators | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operators }) => (this.operators = operators));
  }

  previousState(): void {
    window.history.back();
  }
}
