import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOperators } from 'app/shared/model/operators.model';
import { OperatorsService } from './operators.service';
import { OperatorsDeleteDialogComponent } from './operators-delete-dialog.component';

@Component({
  selector: 'jhi-operators',
  templateUrl: './operators.component.html'
})
export class OperatorsComponent implements OnInit, OnDestroy {
  operators?: IOperators[];
  eventSubscriber?: Subscription;

  constructor(protected operatorsService: OperatorsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.operatorsService.query().subscribe((res: HttpResponse<IOperators[]>) => (this.operators = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOperators();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOperators): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOperators(): void {
    this.eventSubscriber = this.eventManager.subscribe('operatorsListModification', () => this.loadAll());
  }

  delete(operators: IOperators): void {
    const modalRef = this.modalService.open(OperatorsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.operators = operators;
  }
}
