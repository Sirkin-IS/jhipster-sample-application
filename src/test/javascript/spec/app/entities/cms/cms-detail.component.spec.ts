import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CMSDetailComponent } from 'app/entities/cms/cms-detail.component';
import { CMS } from 'app/shared/model/cms.model';

describe('Component Tests', () => {
  describe('CMS Management Detail Component', () => {
    let comp: CMSDetailComponent;
    let fixture: ComponentFixture<CMSDetailComponent>;
    const route = ({ data: of({ cMS: new CMS(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CMSDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CMSDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CMSDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cMS on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cMS).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
