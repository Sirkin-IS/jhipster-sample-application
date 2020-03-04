import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { TransportPackageRepeatComponent } from './transport-package-repeat.component';
import { TransportPackageRepeatDetailComponent } from './transport-package-repeat-detail.component';
import { TransportPackageRepeatUpdateComponent } from './transport-package-repeat-update.component';
import { TransportPackageRepeatDeleteDialogComponent } from './transport-package-repeat-delete-dialog.component';
import { transportPackageRepeatRoute } from './transport-package-repeat.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(transportPackageRepeatRoute)],
  declarations: [
    TransportPackageRepeatComponent,
    TransportPackageRepeatDetailComponent,
    TransportPackageRepeatUpdateComponent,
    TransportPackageRepeatDeleteDialogComponent
  ],
  entryComponents: [TransportPackageRepeatDeleteDialogComponent]
})
export class JhipsterSampleApplicationTransportPackageRepeatModule {}
