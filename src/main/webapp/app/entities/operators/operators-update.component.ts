import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOperators, Operators } from 'app/shared/model/operators.model';
import { OperatorsService } from './operators.service';
import { ITransportPackage } from 'app/shared/model/transport-package.model';
import { TransportPackageService } from 'app/entities/transport-package/transport-package.service';

@Component({
  selector: 'jhi-operators-update',
  templateUrl: './operators-update.component.html'
})
export class OperatorsUpdateComponent implements OnInit {
  isSaving = false;
  transportpackages: ITransportPackage[] = [];

  editForm = this.fb.group({
    id: [],
    operatorId: [null, [Validators.required]],
    url: [null, [Validators.required]],
    secondUrl: [null, [Validators.required]],
    title: [null, [Validators.required]],
    alias: [null, [Validators.required]],
    id: []
  });

  constructor(
    protected operatorsService: OperatorsService,
    protected transportPackageService: TransportPackageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operators }) => {
      this.updateForm(operators);

      this.transportPackageService.query().subscribe((res: HttpResponse<ITransportPackage[]>) => (this.transportpackages = res.body || []));
    });
  }

  updateForm(operators: IOperators): void {
    this.editForm.patchValue({
      id: operators.id,
      operatorId: operators.operatorId,
      url: operators.url,
      secondUrl: operators.secondUrl,
      title: operators.title,
      alias: operators.alias,
      id: operators.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operators = this.createFromForm();
    if (operators.id !== undefined) {
      this.subscribeToSaveResponse(this.operatorsService.update(operators));
    } else {
      this.subscribeToSaveResponse(this.operatorsService.create(operators));
    }
  }

  private createFromForm(): IOperators {
    return {
      ...new Operators(),
      id: this.editForm.get(['id'])!.value,
      operatorId: this.editForm.get(['operatorId'])!.value,
      url: this.editForm.get(['url'])!.value,
      secondUrl: this.editForm.get(['secondUrl'])!.value,
      title: this.editForm.get(['title'])!.value,
      alias: this.editForm.get(['alias'])!.value,
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperators>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITransportPackage): any {
    return item.id;
  }
}
