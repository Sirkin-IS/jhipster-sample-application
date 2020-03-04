import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { HbaseFileComponent } from './hbase-file.component';
import { HbaseFileDetailComponent } from './hbase-file-detail.component';
import { HbaseFileUpdateComponent } from './hbase-file-update.component';
import { HbaseFileDeleteDialogComponent } from './hbase-file-delete-dialog.component';
import { hbaseFileRoute } from './hbase-file.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(hbaseFileRoute)],
  declarations: [HbaseFileComponent, HbaseFileDetailComponent, HbaseFileUpdateComponent, HbaseFileDeleteDialogComponent],
  entryComponents: [HbaseFileDeleteDialogComponent]
})
export class JhipsterSampleApplicationHbaseFileModule {}
