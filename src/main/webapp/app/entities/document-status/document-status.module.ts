import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { DocumentStatusComponent } from './document-status.component';
import { DocumentStatusDetailComponent } from './document-status-detail.component';
import { DocumentStatusUpdateComponent } from './document-status-update.component';
import { DocumentStatusDeleteDialogComponent } from './document-status-delete-dialog.component';
import { documentStatusRoute } from './document-status.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(documentStatusRoute)],
  declarations: [
    DocumentStatusComponent,
    DocumentStatusDetailComponent,
    DocumentStatusUpdateComponent,
    DocumentStatusDeleteDialogComponent
  ],
  entryComponents: [DocumentStatusDeleteDialogComponent]
})
export class JhipsterSampleApplicationDocumentStatusModule {}
