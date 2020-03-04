import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransportPackageDetailComponent } from 'app/entities/transport-package/transport-package-detail.component';
import { TransportPackage } from 'app/shared/model/transport-package.model';

describe('Component Tests', () => {
  describe('TransportPackage Management Detail Component', () => {
    let comp: TransportPackageDetailComponent;
    let fixture: ComponentFixture<TransportPackageDetailComponent>;
    const route = ({ data: of({ transportPackage: new TransportPackage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransportPackageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransportPackageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransportPackageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transportPackage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transportPackage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
