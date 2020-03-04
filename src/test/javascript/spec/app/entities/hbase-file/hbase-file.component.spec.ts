import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HbaseFileComponent } from 'app/entities/hbase-file/hbase-file.component';
import { HbaseFileService } from 'app/entities/hbase-file/hbase-file.service';
import { HbaseFile } from 'app/shared/model/hbase-file.model';

describe('Component Tests', () => {
  describe('HbaseFile Management Component', () => {
    let comp: HbaseFileComponent;
    let fixture: ComponentFixture<HbaseFileComponent>;
    let service: HbaseFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [HbaseFileComponent]
      })
        .overrideTemplate(HbaseFileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HbaseFileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HbaseFileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HbaseFile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hbaseFiles && comp.hbaseFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
