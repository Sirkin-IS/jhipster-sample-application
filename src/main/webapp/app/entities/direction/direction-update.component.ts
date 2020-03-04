import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDirection, Direction } from 'app/shared/model/direction.model';
import { DirectionService } from './direction.service';
import { ILogicalMessage } from 'app/shared/model/logical-message.model';
import { LogicalMessageService } from 'app/entities/logical-message/logical-message.service';

@Component({
  selector: 'jhi-direction-update',
  templateUrl: './direction-update.component.html'
})
export class DirectionUpdateComponent implements OnInit {
  isSaving = false;
  logicalmessages: ILogicalMessage[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    id: []
  });

  constructor(
    protected directionService: DirectionService,
    protected logicalMessageService: LogicalMessageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ direction }) => {
      this.updateForm(direction);

      this.logicalMessageService.query().subscribe((res: HttpResponse<ILogicalMessage[]>) => (this.logicalmessages = res.body || []));
    });
  }

  updateForm(direction: IDirection): void {
    this.editForm.patchValue({
      id: direction.id,
      name: direction.name,
      id: direction.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const direction = this.createFromForm();
    if (direction.id !== undefined) {
      this.subscribeToSaveResponse(this.directionService.update(direction));
    } else {
      this.subscribeToSaveResponse(this.directionService.create(direction));
    }
  }

  private createFromForm(): IDirection {
    return {
      ...new Direction(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDirection>>): void {
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
