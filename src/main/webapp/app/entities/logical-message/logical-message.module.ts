import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { LogicalMessageComponent } from './logical-message.component';
import { LogicalMessageDetailComponent } from './logical-message-detail.component';
import { LogicalMessageUpdateComponent } from './logical-message-update.component';
import { LogicalMessageDeleteDialogComponent } from './logical-message-delete-dialog.component';
import { logicalMessageRoute } from './logical-message.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(logicalMessageRoute)],
  declarations: [
    LogicalMessageComponent,
    LogicalMessageDetailComponent,
    LogicalMessageUpdateComponent,
    LogicalMessageDeleteDialogComponent
  ],
  entryComponents: [LogicalMessageDeleteDialogComponent]
})
export class JhipsterSampleApplicationLogicalMessageModule {}
