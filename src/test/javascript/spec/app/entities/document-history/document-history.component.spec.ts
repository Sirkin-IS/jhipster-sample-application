import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentHistoryComponent } from 'app/entities/document-history/document-history.component';
import { DocumentHistoryService } from 'app/entities/document-history/document-history.service';
import { DocumentHistory } from 'app/shared/model/document-history.model';

describe('Component Tests', () => {
  describe('DocumentHistory Management Component', () => {
    let comp: DocumentHistoryComponent;
    let fixture: ComponentFixture<DocumentHistoryComponent>;
    let service: DocumentHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DocumentHistoryComponent]
      })
        .overrideTemplate(DocumentHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocumentHistory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.documentHistories && comp.documentHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
