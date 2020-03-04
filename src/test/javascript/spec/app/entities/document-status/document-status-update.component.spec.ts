import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentStatusUpdateComponent } from 'app/entities/document-status/document-status-update.component';
import { DocumentStatusService } from 'app/entities/document-status/document-status.service';
import { DocumentStatus } from 'app/shared/model/document-status.model';

describe('Component Tests', () => {
  describe('DocumentStatus Management Update Component', () => {
    let comp: DocumentStatusUpdateComponent;
    let fixture: ComponentFixture<DocumentStatusUpdateComponent>;
    let service: DocumentStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DocumentStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DocumentStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentStatus(123);
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
        const entity = new DocumentStatus();
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
