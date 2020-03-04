import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MessageTypeUpdateComponent } from 'app/entities/message-type/message-type-update.component';
import { MessageTypeService } from 'app/entities/message-type/message-type.service';
import { MessageType } from 'app/shared/model/message-type.model';

describe('Component Tests', () => {
  describe('MessageType Management Update Component', () => {
    let comp: MessageTypeUpdateComponent;
    let fixture: ComponentFixture<MessageTypeUpdateComponent>;
    let service: MessageTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [MessageTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MessageTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MessageTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MessageTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MessageType(123);
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
        const entity = new MessageType();
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
