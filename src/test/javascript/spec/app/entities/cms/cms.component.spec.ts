import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CMSComponent } from 'app/entities/cms/cms.component';
import { CMSService } from 'app/entities/cms/cms.service';
import { CMS } from 'app/shared/model/cms.model';

describe('Component Tests', () => {
  describe('CMS Management Component', () => {
    let comp: CMSComponent;
    let fixture: ComponentFixture<CMSComponent>;
    let service: CMSService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CMSComponent]
      })
        .overrideTemplate(CMSComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CMSComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CMSService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CMS(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cMS && comp.cMS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
