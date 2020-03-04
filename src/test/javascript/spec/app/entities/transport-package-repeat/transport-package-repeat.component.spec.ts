import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransportPackageRepeatComponent } from 'app/entities/transport-package-repeat/transport-package-repeat.component';
import { TransportPackageRepeatService } from 'app/entities/transport-package-repeat/transport-package-repeat.service';
import { TransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';

describe('Component Tests', () => {
  describe('TransportPackageRepeat Management Component', () => {
    let comp: TransportPackageRepeatComponent;
    let fixture: ComponentFixture<TransportPackageRepeatComponent>;
    let service: TransportPackageRepeatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransportPackageRepeatComponent]
      })
        .overrideTemplate(TransportPackageRepeatComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportPackageRepeatComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportPackageRepeatService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransportPackageRepeat(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transportPackageRepeats && comp.transportPackageRepeats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
