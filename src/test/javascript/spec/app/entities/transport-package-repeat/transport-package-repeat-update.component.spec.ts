import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransportPackageRepeatUpdateComponent } from 'app/entities/transport-package-repeat/transport-package-repeat-update.component';
import { TransportPackageRepeatService } from 'app/entities/transport-package-repeat/transport-package-repeat.service';
import { TransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';

describe('Component Tests', () => {
  describe('TransportPackageRepeat Management Update Component', () => {
    let comp: TransportPackageRepeatUpdateComponent;
    let fixture: ComponentFixture<TransportPackageRepeatUpdateComponent>;
    let service: TransportPackageRepeatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransportPackageRepeatUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransportPackageRepeatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportPackageRepeatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportPackageRepeatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransportPackageRepeat(123);
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
        const entity = new TransportPackageRepeat();
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
