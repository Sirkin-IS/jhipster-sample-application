import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentStatusComponent } from 'app/entities/document-status/document-status.component';
import { DocumentStatusService } from 'app/entities/document-status/document-status.service';
import { DocumentStatus } from 'app/shared/model/document-status.model';

describe('Component Tests', () => {
  describe('DocumentStatus Management Component', () => {
    let comp: DocumentStatusComponent;
    let fixture: ComponentFixture<DocumentStatusComponent>;
    let service: DocumentStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DocumentStatusComponent]
      })
        .overrideTemplate(DocumentStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocumentStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.documentStatuses && comp.documentStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
