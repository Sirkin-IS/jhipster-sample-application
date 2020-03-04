import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HbaseFileDetailComponent } from 'app/entities/hbase-file/hbase-file-detail.component';
import { HbaseFile } from 'app/shared/model/hbase-file.model';

describe('Component Tests', () => {
  describe('HbaseFile Management Detail Component', () => {
    let comp: HbaseFileDetailComponent;
    let fixture: ComponentFixture<HbaseFileDetailComponent>;
    const route = ({ data: of({ hbaseFile: new HbaseFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [HbaseFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HbaseFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HbaseFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hbaseFile on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hbaseFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
