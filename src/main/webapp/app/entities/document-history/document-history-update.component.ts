import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDocumentHistory, DocumentHistory } from 'app/shared/model/document-history.model';
import { DocumentHistoryService } from './document-history.service';

@Component({
  selector: 'jhi-document-history-update',
  templateUrl: './document-history-update.component.html'
})
export class DocumentHistoryUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    ownerId: [null, [Validators.required]],
    directionId: [null, [Validators.required]],
    documentId: [null, [Validators.required]],
    statusId: [null, [Validators.required]],
    date: [null, [Validators.required]]
  });

  constructor(
    protected documentHistoryService: DocumentHistoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentHistory }) => {
      this.updateForm(documentHistory);
    });
  }

  updateForm(documentHistory: IDocumentHistory): void {
    this.editForm.patchValue({
      id: documentHistory.id,
      ownerId: documentHistory.ownerId,
      directionId: documentHistory.directionId,
      documentId: documentHistory.documentId,
      statusId: documentHistory.statusId,
      date: documentHistory.date
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentHistory = this.createFromForm();
    if (documentHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.documentHistoryService.update(documentHistory));
    } else {
      this.subscribeToSaveResponse(this.documentHistoryService.create(documentHistory));
    }
  }

  private createFromForm(): IDocumentHistory {
    return {
      ...new DocumentHistory(),
      id: this.editForm.get(['id'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
      directionId: this.editForm.get(['directionId'])!.value,
      documentId: this.editForm.get(['documentId'])!.value,
      statusId: this.editForm.get(['statusId'])!.value,
      date: this.editForm.get(['date'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentHistory>>): void {
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
