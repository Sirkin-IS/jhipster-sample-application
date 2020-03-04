import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BadIncomingTransportPackageDetailComponent } from 'app/entities/bad-incoming-transport-package/bad-incoming-transport-package-detail.component';
import { BadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';

describe('Component Tests', () => {
  describe('BadIncomingTransportPackage Management Detail Component', () => {
    let comp: BadIncomingTransportPackageDetailComponent;
    let fixture: ComponentFixture<BadIncomingTransportPackageDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ badIncomingTransportPackage: new BadIncomingTransportPackage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BadIncomingTransportPackageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BadIncomingTransportPackageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BadIncomingTransportPackageDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load badIncomingTransportPackage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.badIncomingTransportPackage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
