import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReceipt, Receipt } from 'app/shared/model/receipt.model';
import { ReceiptService } from './receipt.service';

@Component({
  selector: 'jhi-receipt-update',
  templateUrl: './receipt-update.component.html'
})
export class ReceiptUpdateComponent implements OnInit {
  isSaving = false;
  receiptDateDp: any;
  createdAtDp: any;

  editForm = this.fb.group({
    id: [],
    cmsId: [null, [Validators.required]],
    receiptTypeId: [null, [Validators.required]],
    receiptDate: [null, [Validators.required]],
    createdAt: [null, [Validators.required]]
  });

  constructor(protected receiptService: ReceiptService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receipt }) => {
      this.updateForm(receipt);
    });
  }

  updateForm(receipt: IReceipt): void {
    this.editForm.patchValue({
      id: receipt.id,
      cmsId: receipt.cmsId,
      receiptTypeId: receipt.receiptTypeId,
      receiptDate: receipt.receiptDate,
      createdAt: receipt.createdAt
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receipt = this.createFromForm();
    if (receipt.id !== undefined) {
      this.subscribeToSaveResponse(this.receiptService.update(receipt));
    } else {
      this.subscribeToSaveResponse(this.receiptService.create(receipt));
    }
  }

  private createFromForm(): IReceipt {
    return {
      ...new Receipt(),
      id: this.editForm.get(['id'])!.value,
      cmsId: this.editForm.get(['cmsId'])!.value,
      receiptTypeId: this.editForm.get(['receiptTypeId'])!.value,
      receiptDate: this.editForm.get(['receiptDate'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceipt>>): void {
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
