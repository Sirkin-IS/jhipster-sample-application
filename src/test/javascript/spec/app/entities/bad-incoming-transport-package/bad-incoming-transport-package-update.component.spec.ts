import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BadIncomingTransportPackageUpdateComponent } from 'app/entities/bad-incoming-transport-package/bad-incoming-transport-package-update.component';
import { BadIncomingTransportPackageService } from 'app/entities/bad-incoming-transport-package/bad-incoming-transport-package.service';
import { BadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';

describe('Component Tests', () => {
  describe('BadIncomingTransportPackage Management Update Component', () => {
    let comp: BadIncomingTransportPackageUpdateComponent;
    let fixture: ComponentFixture<BadIncomingTransportPackageUpdateComponent>;
    let service: BadIncomingTransportPackageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BadIncomingTransportPackageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BadIncomingTransportPackageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BadIncomingTransportPackageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BadIncomingTransportPackageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BadIncomingTransportPackage(123);
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
        const entity = new BadIncomingTransportPackage();
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
