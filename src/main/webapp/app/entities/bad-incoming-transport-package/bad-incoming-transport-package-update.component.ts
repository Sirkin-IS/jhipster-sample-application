import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IBadIncomingTransportPackage, BadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';
import { BadIncomingTransportPackageService } from './bad-incoming-transport-package.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-bad-incoming-transport-package-update',
  templateUrl: './bad-incoming-transport-package-update.component.html'
})
export class BadIncomingTransportPackageUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    transportPackageId: [null, [Validators.required]],
    date: [null, [Validators.required]],
    content: [null, [Validators.required]],
    contentContentType: [],
    answerCode: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected badIncomingTransportPackageService: BadIncomingTransportPackageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badIncomingTransportPackage }) => {
      this.updateForm(badIncomingTransportPackage);
    });
  }

  updateForm(badIncomingTransportPackage: IBadIncomingTransportPackage): void {
    this.editForm.patchValue({
      id: badIncomingTransportPackage.id,
      transportPackageId: badIncomingTransportPackage.transportPackageId,
      date: badIncomingTransportPackage.date,
      content: badIncomingTransportPackage.content,
      contentContentType: badIncomingTransportPackage.contentContentType,
      answerCode: badIncomingTransportPackage.answerCode
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jhipsterSampleApplicationApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const badIncomingTransportPackage = this.createFromForm();
    if (badIncomingTransportPackage.id !== undefined) {
      this.subscribeToSaveResponse(this.badIncomingTransportPackageService.update(badIncomingTransportPackage));
    } else {
      this.subscribeToSaveResponse(this.badIncomingTransportPackageService.create(badIncomingTransportPackage));
    }
  }

  private createFromForm(): IBadIncomingTransportPackage {
    return {
      ...new BadIncomingTransportPackage(),
      id: this.editForm.get(['id'])!.value,
      transportPackageId: this.editForm.get(['transportPackageId'])!.value,
      date: this.editForm.get(['date'])!.value,
      contentContentType: this.editForm.get(['contentContentType'])!.value,
      content: this.editForm.get(['content'])!.value,
      answerCode: this.editForm.get(['answerCode'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBadIncomingTransportPackage>>): void {
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
