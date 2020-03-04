import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OperatorsComponent } from 'app/entities/operators/operators.component';
import { OperatorsService } from 'app/entities/operators/operators.service';
import { Operators } from 'app/shared/model/operators.model';

describe('Component Tests', () => {
  describe('Operators Management Component', () => {
    let comp: OperatorsComponent;
    let fixture: ComponentFixture<OperatorsComponent>;
    let service: OperatorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [OperatorsComponent]
      })
        .overrideTemplate(OperatorsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperatorsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperatorsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Operators(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.operators && comp.operators[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
