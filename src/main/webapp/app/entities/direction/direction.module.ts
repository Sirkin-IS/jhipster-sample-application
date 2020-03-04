import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { DirectionComponent } from './direction.component';
import { DirectionDetailComponent } from './direction-detail.component';
import { DirectionUpdateComponent } from './direction-update.component';
import { DirectionDeleteDialogComponent } from './direction-delete-dialog.component';
import { directionRoute } from './direction.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(directionRoute)],
  declarations: [DirectionComponent, DirectionDetailComponent, DirectionUpdateComponent, DirectionDeleteDialogComponent],
  entryComponents: [DirectionDeleteDialogComponent]
})
export class JhipsterSampleApplicationDirectionModule {}
