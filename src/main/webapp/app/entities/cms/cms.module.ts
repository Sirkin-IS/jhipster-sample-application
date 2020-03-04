import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CMSComponent } from './cms.component';
import { CMSDetailComponent } from './cms-detail.component';
import { CMSUpdateComponent } from './cms-update.component';
import { CMSDeleteDialogComponent } from './cms-delete-dialog.component';
import { cMSRoute } from './cms.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(cMSRoute)],
  declarations: [CMSComponent, CMSDetailComponent, CMSUpdateComponent, CMSDeleteDialogComponent],
  entryComponents: [CMSDeleteDialogComponent]
})
export class JhipsterSampleApplicationCMSModule {}
