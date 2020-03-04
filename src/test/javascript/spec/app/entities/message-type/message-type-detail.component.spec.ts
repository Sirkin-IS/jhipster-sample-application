import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MessageTypeDetailComponent } from 'app/entities/message-type/message-type-detail.component';
import { MessageType } from 'app/shared/model/message-type.model';

describe('Component Tests', () => {
  describe('MessageType Management Detail Component', () => {
    let comp: MessageTypeDetailComponent;
    let fixture: ComponentFixture<MessageTypeDetailComponent>;
    const route = ({ data: of({ messageType: new MessageType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [MessageTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MessageTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MessageTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load messageType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.messageType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
