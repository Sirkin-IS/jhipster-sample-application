import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LogicalMessageDetailComponent } from 'app/entities/logical-message/logical-message-detail.component';
import { LogicalMessage } from 'app/shared/model/logical-message.model';

describe('Component Tests', () => {
  describe('LogicalMessage Management Detail Component', () => {
    let comp: LogicalMessageDetailComponent;
    let fixture: ComponentFixture<LogicalMessageDetailComponent>;
    const route = ({ data: of({ logicalMessage: new LogicalMessage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [LogicalMessageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LogicalMessageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LogicalMessageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load logicalMessage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.logicalMessage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
