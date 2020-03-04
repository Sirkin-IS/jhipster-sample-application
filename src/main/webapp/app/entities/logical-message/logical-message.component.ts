import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILogicalMessage } from 'app/shared/model/logical-message.model';
import { LogicalMessageService } from './logical-message.service';
import { LogicalMessageDeleteDialogComponent } from './logical-message-delete-dialog.component';

@Component({
  selector: 'jhi-logical-message',
  templateUrl: './logical-message.component.html'
})
export class LogicalMessageComponent implements OnInit, OnDestroy {
  logicalMessages?: ILogicalMessage[];
  eventSubscriber?: Subscription;

  constructor(
    protected logicalMessageService: LogicalMessageService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.logicalMessageService.query().subscribe((res: HttpResponse<ILogicalMessage[]>) => (this.logicalMessages = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLogicalMessages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILogicalMessage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLogicalMessages(): void {
    this.eventSubscriber = this.eventManager.subscribe('logicalMessageListModification', () => this.loadAll());
  }

  delete(logicalMessage: ILogicalMessage): void {
    const modalRef = this.modalService.open(LogicalMessageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.logicalMessage = logicalMessage;
  }
}
