import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHbaseFile, HbaseFile } from 'app/shared/model/hbase-file.model';
import { HbaseFileService } from './hbase-file.service';
import { ICMS } from 'app/shared/model/cms.model';
import { CMSService } from 'app/entities/cms/cms.service';

@Component({
  selector: 'jhi-hbase-file-update',
  templateUrl: './hbase-file-update.component.html'
})
export class HbaseFileUpdateComponent implements OnInit {
  isSaving = false;
  cms: ICMS[] = [];

  editForm = this.fb.group({
    id: [],
    fileId: [null, [Validators.required]],
    content: [null, [Validators.required]],
    fileId: []
  });

  constructor(
    protected hbaseFileService: HbaseFileService,
    protected cMSService: CMSService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hbaseFile }) => {
      this.updateForm(hbaseFile);

      this.cMSService.query().subscribe((res: HttpResponse<ICMS[]>) => (this.cms = res.body || []));
    });
  }

  updateForm(hbaseFile: IHbaseFile): void {
    this.editForm.patchValue({
      id: hbaseFile.id,
      fileId: hbaseFile.fileId,
      content: hbaseFile.content,
      fileId: hbaseFile.fileId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hbaseFile = this.createFromForm();
    if (hbaseFile.id !== undefined) {
      this.subscribeToSaveResponse(this.hbaseFileService.update(hbaseFile));
    } else {
      this.subscribeToSaveResponse(this.hbaseFileService.create(hbaseFile));
    }
  }

  private createFromForm(): IHbaseFile {
    return {
      ...new HbaseFile(),
      id: this.editForm.get(['id'])!.value,
      fileId: this.editForm.get(['fileId'])!.value,
      content: this.editForm.get(['content'])!.value,
      fileId: this.editForm.get(['fileId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHbaseFile>>): void {
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

  trackById(index: number, item: ICMS): any {
    return item.id;
  }
}
