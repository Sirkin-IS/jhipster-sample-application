import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransportPackageUpdateComponent } from 'app/entities/transport-package/transport-package-update.component';
import { TransportPackageService } from 'app/entities/transport-package/transport-package.service';
import { TransportPackage } from 'app/shared/model/transport-package.model';

describe('Component Tests', () => {
  describe('TransportPackage Management Update Component', () => {
    let comp: TransportPackageUpdateComponent;
    let fixture: ComponentFixture<TransportPackageUpdateComponent>;
    let service: TransportPackageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransportPackageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransportPackageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportPackageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportPackageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransportPackage(123);
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
        const entity = new TransportPackage();
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
