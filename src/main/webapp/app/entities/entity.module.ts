import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'message-type',
        loadChildren: () => import('./message-type/message-type.module').then(m => m.JhipsterSampleApplicationMessageTypeModule)
      },
      {
        path: 'direction',
        loadChildren: () => import('./direction/direction.module').then(m => m.JhipsterSampleApplicationDirectionModule)
      },
      {
        path: 'document-status',
        loadChildren: () => import('./document-status/document-status.module').then(m => m.JhipsterSampleApplicationDocumentStatusModule)
      },
      {
        path: 'cms',
        loadChildren: () => import('./cms/cms.module').then(m => m.JhipsterSampleApplicationCMSModule)
      },
      {
        path: 'logical-message',
        loadChildren: () => import('./logical-message/logical-message.module').then(m => m.JhipsterSampleApplicationLogicalMessageModule)
      },
      {
        path: 'receipt',
        loadChildren: () => import('./receipt/receipt.module').then(m => m.JhipsterSampleApplicationReceiptModule)
      },
      {
        path: 'transport-package',
        loadChildren: () =>
          import('./transport-package/transport-package.module').then(m => m.JhipsterSampleApplicationTransportPackageModule)
      },
      {
        path: 'operators',
        loadChildren: () => import('./operators/operators.module').then(m => m.JhipsterSampleApplicationOperatorsModule)
      },
      {
        path: 'transport-package-repeat',
        loadChildren: () =>
          import('./transport-package-repeat/transport-package-repeat.module').then(
            m => m.JhipsterSampleApplicationTransportPackageRepeatModule
          )
      },
      {
        path: 'bad-incoming-transport-package',
        loadChildren: () =>
          import('./bad-incoming-transport-package/bad-incoming-transport-package.module').then(
            m => m.JhipsterSampleApplicationBadIncomingTransportPackageModule
          )
      },
      {
        path: 'document-history',
        loadChildren: () => import('./document-history/document-history.module').then(m => m.JhipsterSampleApplicationDocumentHistoryModule)
      },
      {
        path: 'hbase-file',
        loadChildren: () => import('./hbase-file/hbase-file.module').then(m => m.JhipsterSampleApplicationHbaseFileModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterSampleApplicationEntityModule {}
