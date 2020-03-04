import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogicalMessage } from 'app/shared/model/logical-message.model';

@Component({
  selector: 'jhi-logical-message-detail',
  templateUrl: './logical-message-detail.component.html'
})
export class LogicalMessageDetailComponent implements OnInit {
  logicalMessage: ILogicalMessage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logicalMessage }) => (this.logicalMessage = logicalMessage));
  }

  previousState(): void {
    window.history.back();
  }
}
