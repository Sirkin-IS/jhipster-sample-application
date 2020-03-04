import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILogicalMessage, LogicalMessage } from 'app/shared/model/logical-message.model';
import { LogicalMessageService } from './logical-message.service';
import { ITransportPackage } from 'app/shared/model/transport-package.model';
import { TransportPackageService } from 'app/entities/transport-package/transport-package.service';
import { IReceipt } from 'app/shared/model/receipt.model';
import { ReceiptService } from 'app/entities/receipt/receipt.service';

type SelectableEntity = ITransportPackage | IReceipt;

@Component({
  selector: 'jhi-logical-message-update',
  templateUrl: './logical-message-update.component.html'
})
export class LogicalMessageUpdateComponent implements OnInit {
  isSaving = false;
  transportpackageids: ITransportPackage[] = [];
  receiptids: IReceipt[] = [];
  lastTimeOfAttempsDp: any;
  createdAtDp: any;
  updatedAtDp: any;

  editForm = this.fb.group({
    id: [],
    ownerId: [null, [Validators.required]],
    documentId: [null, [Validators.required]],
    eventId: [],
    directionId: [null, [Validators.required]],
    cmsId: [null, [Validators.required]],
    cmsDirectoryName: [null, [Validators.required]],
    transportPackageId: [],
    receiptId: [],
    messageTypeId: [],
    responseCode: [],
    attemps: [],
    lastTimeOfAttemps: [],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    transportPackageId: [],
    receiptId: []
  });

  constructor(
    protected logicalMessageService: LogicalMessageService,
    protected transportPackageService: TransportPackageService,
    protected receiptService: ReceiptService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logicalMessage }) => {
      this.updateForm(logicalMessage);

      this.transportPackageService
        .query({ filter: 'transportpackageid-is-null' })
        .pipe(
          map((res: HttpResponse<ITransportPackage[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITransportPackage[]) => {
          if (!logicalMessage.transportPackageId || !logicalMessage.transportPackageId.id) {
            this.transportpackageids = resBody;
          } else {
            this.transportPackageService
              .find(logicalMessage.transportPackageId.id)
              .pipe(
                map((subRes: HttpResponse<ITransportPackage>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITransportPackage[]) => (this.transportpackageids = concatRes));
          }
        });

      this.receiptService
        .query({ filter: 'id-is-null' })
        .pipe(
          map((res: HttpResponse<IReceipt[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IReceipt[]) => {
          if (!logicalMessage.receiptId || !logicalMessage.receiptId.id) {
            this.receiptids = resBody;
          } else {
            this.receiptService
              .find(logicalMessage.receiptId.id)
              .pipe(
                map((subRes: HttpResponse<IReceipt>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IReceipt[]) => (this.receiptids = concatRes));
          }
        });
    });
  }

  updateForm(logicalMessage: ILogicalMessage): void {
    this.editForm.patchValue({
      id: logicalMessage.id,
      ownerId: logicalMessage.ownerId,
      documentId: logicalMessage.documentId,
      eventId: logicalMessage.eventId,
      directionId: logicalMessage.directionId,
      cmsId: logicalMessage.cmsId,
      cmsDirectoryName: logicalMessage.cmsDirectoryName,
      transportPackageId: logicalMessage.transportPackageId,
      receiptId: logicalMessage.receiptId,
      messageTypeId: logicalMessage.messageTypeId,
      responseCode: logicalMessage.responseCode,
      attemps: logicalMessage.attemps,
      lastTimeOfAttemps: logicalMessage.lastTimeOfAttemps,
      createdAt: logicalMessage.createdAt,
      updatedAt: logicalMessage.updatedAt,
      transportPackageId: logicalMessage.transportPackageId,
      receiptId: logicalMessage.receiptId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logicalMessage = this.createFromForm();
    if (logicalMessage.id !== undefined) {
      this.subscribeToSaveResponse(this.logicalMessageService.update(logicalMessage));
    } else {
      this.subscribeToSaveResponse(this.logicalMessageService.create(logicalMessage));
    }
  }

  private createFromForm(): ILogicalMessage {
    return {
      ...new LogicalMessage(),
      id: this.editForm.get(['id'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
      documentId: this.editForm.get(['documentId'])!.value,
      eventId: this.editForm.get(['eventId'])!.value,
      directionId: this.editForm.get(['directionId'])!.value,
      cmsId: this.editForm.get(['cmsId'])!.value,
      cmsDirectoryName: this.editForm.get(['cmsDirectoryName'])!.value,
      transportPackageId: this.editForm.get(['transportPackageId'])!.value,
      receiptId: this.editForm.get(['receiptId'])!.value,
      messageTypeId: this.editForm.get(['messageTypeId'])!.value,
      responseCode: this.editForm.get(['responseCode'])!.value,
      attemps: this.editForm.get(['attemps'])!.value,
      lastTimeOfAttemps: this.editForm.get(['lastTimeOfAttemps'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      transportPackageId: this.editForm.get(['transportPackageId'])!.value,
      receiptId: this.editForm.get(['receiptId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogicalMessage>>): void {
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
