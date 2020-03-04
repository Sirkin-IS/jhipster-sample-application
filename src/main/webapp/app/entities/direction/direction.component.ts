import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDirection } from 'app/shared/model/direction.model';
import { DirectionService } from './direction.service';
import { DirectionDeleteDialogComponent } from './direction-delete-dialog.component';

@Component({
  selector: 'jhi-direction',
  templateUrl: './direction.component.html'
})
export class DirectionComponent implements OnInit, OnDestroy {
  directions?: IDirection[];
  eventSubscriber?: Subscription;

  constructor(protected directionService: DirectionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.directionService.query().subscribe((res: HttpResponse<IDirection[]>) => (this.directions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDirections();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDirection): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDirections(): void {
    this.eventSubscriber = this.eventManager.subscribe('directionListModification', () => this.loadAll());
  }

  delete(direction: IDirection): void {
    const modalRef = this.modalService.open(DirectionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.direction = direction;
  }
}
