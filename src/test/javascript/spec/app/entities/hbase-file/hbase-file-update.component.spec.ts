import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HbaseFileUpdateComponent } from 'app/entities/hbase-file/hbase-file-update.component';
import { HbaseFileService } from 'app/entities/hbase-file/hbase-file.service';
import { HbaseFile } from 'app/shared/model/hbase-file.model';

describe('Component Tests', () => {
  describe('HbaseFile Management Update Component', () => {
    let comp: HbaseFileUpdateComponent;
    let fixture: ComponentFixture<HbaseFileUpdateComponent>;
    let service: HbaseFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [HbaseFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HbaseFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HbaseFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HbaseFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HbaseFile(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new HbaseFile();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
