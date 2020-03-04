import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICMS, CMS } from 'app/shared/model/cms.model';
import { CMSService } from './cms.service';
import { ILogicalMessage } from 'app/shared/model/logical-message.model';
import { LogicalMessageService } from 'app/entities/logical-message/logical-message.service';

@Component({
  selector: 'jhi-cms-update',
  templateUrl: './cms-update.component.html'
})
export class CMSUpdateComponent implements OnInit {
  isSaving = false;
  logicalmessages: ILogicalMessage[] = [];
  createdAtDp: any;

  editForm = this.fb.group({
    id: [],
    fileId: [null, [Validators.required]],
    messageCount: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    id: []
  });

  constructor(
    protected cMSService: CMSService,
    protected logicalMessageService: LogicalMessageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cMS }) => {
      this.updateForm(cMS);

      this.logicalMessageService.query().subscribe((res: HttpResponse<ILogicalMessage[]>) => (this.logicalmessages = res.body || []));
    });
  }

  updateForm(cMS: ICMS): void {
    this.editForm.patchValue({
      id: cMS.id,
      fileId: cMS.fileId,
      messageCount: cMS.messageCount,
      createdAt: cMS.createdAt,
      id: cMS.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cMS = this.createFromForm();
    if (cMS.id !== undefined) {
      this.subscribeToSaveResponse(this.cMSService.update(cMS));
    } else {
      this.subscribeToSaveResponse(this.cMSService.create(cMS));
    }
  }

  private createFromForm(): ICMS {
    return {
      ...new CMS(),
      id: this.editForm.get(['id'])!.value,
      fileId: this.editForm.get(['fileId'])!.value,
      messageCount: this.editForm.get(['messageCount'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICMS>>): void {
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

  trackById(index: number, item: ILogicalMessage): any {
    return item.id;
  }
}
