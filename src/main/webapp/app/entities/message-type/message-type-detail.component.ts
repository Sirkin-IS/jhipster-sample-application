import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMessageType } from 'app/shared/model/message-type.model';

@Component({
  selector: 'jhi-message-type-detail',
  templateUrl: './message-type-detail.component.html'
})
export class MessageTypeDetailComponent implements OnInit {
  messageType: IMessageType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ messageType }) => (this.messageType = messageType));
  }

  previousState(): void {
    window.history.back();
  }
}
