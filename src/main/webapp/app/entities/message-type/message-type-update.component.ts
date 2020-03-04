import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMessageType, MessageType } from 'app/shared/model/message-type.model';
import { MessageTypeService } from './message-type.service';
import { ILogicalMessage } from 'app/shared/model/logical-message.model';
import { LogicalMessageService } from 'app/entities/logical-message/logical-message.service';

@Component({
  selector: 'jhi-message-type-update',
  templateUrl: './message-type-update.component.html'
})
export class MessageTypeUpdateComponent implements OnInit {
  isSaving = false;
  logicalmessages: ILogicalMessage[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    id: []
  });

  constructor(
    protected messageTypeService: MessageTypeService,
    protected logicalMessageService: LogicalMessageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ messageType }) => {
      this.updateForm(messageType);

      this.logicalMessageService.query().subscribe((res: HttpResponse<ILogicalMessage[]>) => (this.logicalmessages = res.body || []));
    });
  }

  updateForm(messageType: IMessageType): void {
    this.editForm.patchValue({
      id: messageType.id,
      name: messageType.name,
      id: messageType.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const messageType = this.createFromForm();
    if (messageType.id !== undefined) {
      this.subscribeToSaveResponse(this.messageTypeService.update(messageType));
    } else {
      this.subscribeToSaveResponse(this.messageTypeService.create(messageType));
    }
  }

  private createFromForm(): IMessageType {
    return {
      ...new MessageType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessageType>>): void {
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
