import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BadIncomingTransportPackageComponent } from 'app/entities/bad-incoming-transport-package/bad-incoming-transport-package.component';
import { BadIncomingTransportPackageService } from 'app/entities/bad-incoming-transport-package/bad-incoming-transport-package.service';
import { BadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';

describe('Component Tests', () => {
  describe('BadIncomingTransportPackage Management Component', () => {
    let comp: BadIncomingTransportPackageComponent;
    let fixture: ComponentFixture<BadIncomingTransportPackageComponent>;
    let service: BadIncomingTransportPackageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BadIncomingTransportPackageComponent]
      })
        .overrideTemplate(BadIncomingTransportPackageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BadIncomingTransportPackageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BadIncomingTransportPackageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BadIncomingTransportPackage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.badIncomingTransportPackages && comp.badIncomingTransportPackages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
