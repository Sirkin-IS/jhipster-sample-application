import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { MessageTypeComponent } from './message-type.component';
import { MessageTypeDetailComponent } from './message-type-detail.component';
import { MessageTypeUpdateComponent } from './message-type-update.component';
import { MessageTypeDeleteDialogComponent } from './message-type-delete-dialog.component';
import { messageTypeRoute } from './message-type.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(messageTypeRoute)],
  declarations: [MessageTypeComponent, MessageTypeDetailComponent, MessageTypeUpdateComponent, MessageTypeDeleteDialogComponent],
  entryComponents: [MessageTypeDeleteDialogComponent]
})
export class JhipsterSampleApplicationMessageTypeModule {}
