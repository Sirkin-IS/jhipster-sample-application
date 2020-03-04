import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { TransportPackageComponent } from './transport-package.component';
import { TransportPackageDetailComponent } from './transport-package-detail.component';
import { TransportPackageUpdateComponent } from './transport-package-update.component';
import { TransportPackageDeleteDialogComponent } from './transport-package-delete-dialog.component';
import { transportPackageRoute } from './transport-package.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(transportPackageRoute)],
  declarations: [
    TransportPackageComponent,
    TransportPackageDetailComponent,
    TransportPackageUpdateComponent,
    TransportPackageDeleteDialogComponent
  ],
  entryComponents: [TransportPackageDeleteDialogComponent]
})
export class JhipsterSampleApplicationTransportPackageModule {}
