import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentStatus } from 'app/shared/model/document-status.model';

@Component({
  selector: 'jhi-document-status-detail',
  templateUrl: './document-status-detail.component.html'
})
export class DocumentStatusDetailComponent implements OnInit {
  documentStatus: IDocumentStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentStatus }) => (this.documentStatus = documentStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
