import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransportPackageRepeat, TransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';
import { TransportPackageRepeatService } from './transport-package-repeat.service';

@Component({
  selector: 'jhi-transport-package-repeat-update',
  templateUrl: './transport-package-repeat-update.component.html'
})
export class TransportPackageRepeatUpdateComponent implements OnInit {
  isSaving = false;
  createdAtDp: any;

  editForm = this.fb.group({
    id: [],
    transportPackageId: [null, [Validators.required]],
    repeatNum: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    answerCode: [null, [Validators.required]],
    answerContent: [null, [Validators.required]]
  });

  constructor(
    protected transportPackageRepeatService: TransportPackageRepeatService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transportPackageRepeat }) => {
      this.updateForm(transportPackageRepeat);
    });
  }

  updateForm(transportPackageRepeat: ITransportPackageRepeat): void {
    this.editForm.patchValue({
      id: transportPackageRepeat.id,
      transportPackageId: transportPackageRepeat.transportPackageId,
      repeatNum: transportPackageRepeat.repeatNum,
      createdAt: transportPackageRepeat.createdAt,
      answerCode: transportPackageRepeat.answerCode,
      answerContent: transportPackageRepeat.answerContent
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transportPackageRepeat = this.createFromForm();
    if (transportPackageRepeat.id !== undefined) {
      this.subscribeToSaveResponse(this.transportPackageRepeatService.update(transportPackageRepeat));
    } else {
      this.subscribeToSaveResponse(this.transportPackageRepeatService.create(transportPackageRepeat));
    }
  }

  private createFromForm(): ITransportPackageRepeat {
    return {
      ...new TransportPackageRepeat(),
      id: this.editForm.get(['id'])!.value,
      transportPackageId: this.editForm.get(['transportPackageId'])!.value,
      repeatNum: this.editForm.get(['repeatNum'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      answerCode: this.editForm.get(['answerCode'])!.value,
      answerContent: this.editForm.get(['answerContent'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransportPackageRepeat>>): void {
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
}
