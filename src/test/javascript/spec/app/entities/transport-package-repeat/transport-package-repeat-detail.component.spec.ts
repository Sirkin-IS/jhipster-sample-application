import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransportPackageRepeatDetailComponent } from 'app/entities/transport-package-repeat/transport-package-repeat-detail.component';
import { TransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';

describe('Component Tests', () => {
  describe('TransportPackageRepeat Management Detail Component', () => {
    let comp: TransportPackageRepeatDetailComponent;
    let fixture: ComponentFixture<TransportPackageRepeatDetailComponent>;
    const route = ({ data: of({ transportPackageRepeat: new TransportPackageRepeat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransportPackageRepeatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransportPackageRepeatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransportPackageRepeatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transportPackageRepeat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transportPackageRepeat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
