import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDocumentStatus, DocumentStatus } from 'app/shared/model/document-status.model';
import { DocumentStatusService } from './document-status.service';
import { IDocumentHistory } from 'app/shared/model/document-history.model';
import { DocumentHistoryService } from 'app/entities/document-history/document-history.service';

@Component({
  selector: 'jhi-document-status-update',
  templateUrl: './document-status-update.component.html'
})
export class DocumentStatusUpdateComponent implements OnInit {
  isSaving = false;
  documenthistories: IDocumentHistory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    id: []
  });

  constructor(
    protected documentStatusService: DocumentStatusService,
    protected documentHistoryService: DocumentHistoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentStatus }) => {
      this.updateForm(documentStatus);

      this.documentHistoryService.query().subscribe((res: HttpResponse<IDocumentHistory[]>) => (this.documenthistories = res.body || []));
    });
  }

  updateForm(documentStatus: IDocumentStatus): void {
    this.editForm.patchValue({
      id: documentStatus.id,
      name: documentStatus.name,
      id: documentStatus.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentStatus = this.createFromForm();
    if (documentStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.documentStatusService.update(documentStatus));
    } else {
      this.subscribeToSaveResponse(this.documentStatusService.create(documentStatus));
    }
  }

  private createFromForm(): IDocumentStatus {
    return {
      ...new DocumentStatus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentStatus>>): void {
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

  trackById(index: number, item: IDocumentHistory): any {
    return item.id;
  }
}
