import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BadIncomingTransportPackageComponent } from './bad-incoming-transport-package.component';
import { BadIncomingTransportPackageDetailComponent } from './bad-incoming-transport-package-detail.component';
import { BadIncomingTransportPackageUpdateComponent } from './bad-incoming-transport-package-update.component';
import { BadIncomingTransportPackageDeleteDialogComponent } from './bad-incoming-transport-package-delete-dialog.component';
import { badIncomingTransportPackageRoute } from './bad-incoming-transport-package.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(badIncomingTransportPackageRoute)],
  declarations: [
    BadIncomingTransportPackageComponent,
    BadIncomingTransportPackageDetailComponent,
    BadIncomingTransportPackageUpdateComponent,
    BadIncomingTransportPackageDeleteDialogComponent
  ],
  entryComponents: [BadIncomingTransportPackageDeleteDialogComponent]
})
export class JhipsterSampleApplicationBadIncomingTransportPackageModule {}
