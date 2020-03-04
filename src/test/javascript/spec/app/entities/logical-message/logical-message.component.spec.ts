import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LogicalMessageComponent } from 'app/entities/logical-message/logical-message.component';
import { LogicalMessageService } from 'app/entities/logical-message/logical-message.service';
import { LogicalMessage } from 'app/shared/model/logical-message.model';

describe('Component Tests', () => {
  describe('LogicalMessage Management Component', () => {
    let comp: LogicalMessageComponent;
    let fixture: ComponentFixture<LogicalMessageComponent>;
    let service: LogicalMessageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [LogicalMessageComponent]
      })
        .overrideTemplate(LogicalMessageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogicalMessageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogicalMessageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LogicalMessage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.logicalMessages && comp.logicalMessages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
