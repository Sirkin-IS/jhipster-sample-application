import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMessageType } from 'app/shared/model/message-type.model';
import { MessageTypeService } from './message-type.service';
import { MessageTypeDeleteDialogComponent } from './message-type-delete-dialog.component';

@Component({
  selector: 'jhi-message-type',
  templateUrl: './message-type.component.html'
})
export class MessageTypeComponent implements OnInit, OnDestroy {
  messageTypes?: IMessageType[];
  eventSubscriber?: Subscription;

  constructor(
    protected messageTypeService: MessageTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.messageTypeService.query().subscribe((res: HttpResponse<IMessageType[]>) => (this.messageTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMessageTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMessageType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMessageTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('messageTypeListModification', () => this.loadAll());
  }

  delete(messageType: IMessageType): void {
    const modalRef = this.modalService.open(MessageTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.messageType = messageType;
  }
}
