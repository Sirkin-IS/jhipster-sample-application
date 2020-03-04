import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransportPackage, TransportPackage } from 'app/shared/model/transport-package.model';
import { TransportPackageService } from './transport-package.service';
import { ITransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';
import { TransportPackageRepeatService } from 'app/entities/transport-package-repeat/transport-package-repeat.service';
import { IBadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';
import { BadIncomingTransportPackageService } from 'app/entities/bad-incoming-transport-package/bad-incoming-transport-package.service';

type SelectableEntity = ITransportPackageRepeat | IBadIncomingTransportPackage;

@Component({
  selector: 'jhi-transport-package-update',
  templateUrl: './transport-package-update.component.html'
})
export class TransportPackageUpdateComponent implements OnInit {
  isSaving = false;
  transportpackagerepeats: ITransportPackageRepeat[] = [];
  badincomingtransportpackages: IBadIncomingTransportPackage[] = [];
  lastTimeOfAttempsDp: any;
  createdAtDp: any;

  editForm = this.fb.group({
    id: [],
    transportPackageId: [null, [Validators.required]],
    directionId: [null, [Validators.required]],
    operatorId: [null, [Validators.required]],
    answerCode: [],
    answerContent: [],
    attemps: [],
    lastTimeOfAttemps: [],
    content: [],
    createdAt: [null, [Validators.required]],
    id: [],
    id: []
  });

  constructor(
    protected transportPackageService: TransportPackageService,
    protected transportPackageRepeatService: TransportPackageRepeatService,
    protected badIncomingTransportPackageService: BadIncomingTransportPackageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transportPackage }) => {
      this.updateForm(transportPackage);

      this.transportPackageRepeatService
        .query()
        .subscribe((res: HttpResponse<ITransportPackageRepeat[]>) => (this.transportpackagerepeats = res.body || []));

      this.badIncomingTransportPackageService
        .query()
        .subscribe((res: HttpResponse<IBadIncomingTransportPackage[]>) => (this.badincomingtransportpackages = res.body || []));
    });
  }

  updateForm(transportPackage: ITransportPackage): void {
    this.editForm.patchValue({
      id: transportPackage.id,
      transportPackageId: transportPackage.transportPackageId,
      directionId: transportPackage.directionId,
      operatorId: transportPackage.operatorId,
      answerCode: transportPackage.answerCode,
      answerContent: transportPackage.answerContent,
      attemps: transportPackage.attemps,
      lastTimeOfAttemps: transportPackage.lastTimeOfAttemps,
      content: transportPackage.content,
      createdAt: transportPackage.createdAt,
      id: transportPackage.id,
      id: transportPackage.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transportPackage = this.createFromForm();
    if (transportPackage.id !== undefined) {
      this.subscribeToSaveResponse(this.transportPackageService.update(transportPackage));
    } else {
      this.subscribeToSaveResponse(this.transportPackageService.create(transportPackage));
    }
  }

  private createFromForm(): ITransportPackage {
    return {
      ...new TransportPackage(),
      id: this.editForm.get(['id'])!.value,
      transportPackageId: this.editForm.get(['transportPackageId'])!.value,
      directionId: this.editForm.get(['directionId'])!.value,
      operatorId: this.editForm.get(['operatorId'])!.value,
      answerCode: this.editForm.get(['answerCode'])!.value,
      answerContent: this.editForm.get(['answerContent'])!.value,
      attemps: this.editForm.get(['attemps'])!.value,
      lastTimeOfAttemps: this.editForm.get(['lastTimeOfAttemps'])!.value,
      content: this.editForm.get(['content'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      id: this.editForm.get(['id'])!.value,
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransportPackage>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
