import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OperatorsUpdateComponent } from 'app/entities/operators/operators-update.component';
import { OperatorsService } from 'app/entities/operators/operators.service';
import { Operators } from 'app/shared/model/operators.model';

describe('Component Tests', () => {
  describe('Operators Management Update Component', () => {
    let comp: OperatorsUpdateComponent;
    let fixture: ComponentFixture<OperatorsUpdateComponent>;
    let service: OperatorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [OperatorsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OperatorsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperatorsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperatorsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Operators(123);
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
        const entity = new Operators();
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
