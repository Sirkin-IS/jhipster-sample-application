import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CMSUpdateComponent } from 'app/entities/cms/cms-update.component';
import { CMSService } from 'app/entities/cms/cms.service';
import { CMS } from 'app/shared/model/cms.model';

describe('Component Tests', () => {
  describe('CMS Management Update Component', () => {
    let comp: CMSUpdateComponent;
    let fixture: ComponentFixture<CMSUpdateComponent>;
    let service: CMSService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CMSUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CMSUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CMSUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CMSService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CMS(123);
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
        const entity = new CMS();
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
