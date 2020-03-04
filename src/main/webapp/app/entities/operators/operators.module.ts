import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { OperatorsComponent } from './operators.component';
import { OperatorsDetailComponent } from './operators-detail.component';
import { OperatorsUpdateComponent } from './operators-update.component';
import { OperatorsDeleteDialogComponent } from './operators-delete-dialog.component';
import { operatorsRoute } from './operators.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(operatorsRoute)],
  declarations: [OperatorsComponent, OperatorsDetailComponent, OperatorsUpdateComponent, OperatorsDeleteDialogComponent],
  entryComponents: [OperatorsDeleteDialogComponent]
})
export class JhipsterSampleApplicationOperatorsModule {}
