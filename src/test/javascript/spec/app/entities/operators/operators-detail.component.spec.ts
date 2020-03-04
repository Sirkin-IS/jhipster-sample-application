import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OperatorsDetailComponent } from 'app/entities/operators/operators-detail.component';
import { Operators } from 'app/shared/model/operators.model';

describe('Component Tests', () => {
  describe('Operators Management Detail Component', () => {
    let comp: OperatorsDetailComponent;
    let fixture: ComponentFixture<OperatorsDetailComponent>;
    const route = ({ data: of({ operators: new Operators(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [OperatorsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OperatorsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OperatorsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load operators on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.operators).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
