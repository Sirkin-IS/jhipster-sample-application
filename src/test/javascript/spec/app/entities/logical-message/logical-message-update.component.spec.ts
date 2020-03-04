import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LogicalMessageUpdateComponent } from 'app/entities/logical-message/logical-message-update.component';
import { LogicalMessageService } from 'app/entities/logical-message/logical-message.service';
import { LogicalMessage } from 'app/shared/model/logical-message.model';

describe('Component Tests', () => {
  describe('LogicalMessage Management Update Component', () => {
    let comp: LogicalMessageUpdateComponent;
    let fixture: ComponentFixture<LogicalMessageUpdateComponent>;
    let service: LogicalMessageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [LogicalMessageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LogicalMessageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogicalMessageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogicalMessageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LogicalMessage(123);
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
        const entity = new LogicalMessage();
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
