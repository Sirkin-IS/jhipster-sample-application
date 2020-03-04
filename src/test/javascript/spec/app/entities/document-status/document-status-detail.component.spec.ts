import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentStatusDetailComponent } from 'app/entities/document-status/document-status-detail.component';
import { DocumentStatus } from 'app/shared/model/document-status.model';

describe('Component Tests', () => {
  describe('DocumentStatus Management Detail Component', () => {
    let comp: DocumentStatusDetailComponent;
    let fixture: ComponentFixture<DocumentStatusDetailComponent>;
    const route = ({ data: of({ documentStatus: new DocumentStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DocumentStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DocumentStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load documentStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.documentStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
