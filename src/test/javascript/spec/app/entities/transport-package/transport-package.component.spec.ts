import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransportPackageComponent } from 'app/entities/transport-package/transport-package.component';
import { TransportPackageService } from 'app/entities/transport-package/transport-package.service';
import { TransportPackage } from 'app/shared/model/transport-package.model';

describe('Component Tests', () => {
  describe('TransportPackage Management Component', () => {
    let comp: TransportPackageComponent;
    let fixture: ComponentFixture<TransportPackageComponent>;
    let service: TransportPackageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransportPackageComponent]
      })
        .overrideTemplate(TransportPackageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportPackageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportPackageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransportPackage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transportPackages && comp.transportPackages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
