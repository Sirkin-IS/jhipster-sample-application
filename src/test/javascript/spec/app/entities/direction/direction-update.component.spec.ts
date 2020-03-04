import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DirectionUpdateComponent } from 'app/entities/direction/direction-update.component';
import { DirectionService } from 'app/entities/direction/direction.service';
import { Direction } from 'app/shared/model/direction.model';

describe('Component Tests', () => {
  describe('Direction Management Update Component', () => {
    let comp: DirectionUpdateComponent;
    let fixture: ComponentFixture<DirectionUpdateComponent>;
    let service: DirectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DirectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DirectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DirectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DirectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Direction(123);
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
        const entity = new Direction();
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
