import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LogicalMessageService } from 'app/entities/logical-message/logical-message.service';
import { ILogicalMessage, LogicalMessage } from 'app/shared/model/logical-message.model';

describe('Service Tests', () => {
  describe('LogicalMessage Service', () => {
    let injector: TestBed;
    let service: LogicalMessageService;
    let httpMock: HttpTestingController;
    let elemDefault: ILogicalMessage;
    let expectedResult: ILogicalMessage | ILogicalMessage[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LogicalMessageService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LogicalMessage(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        0,
        0,
        currentDate,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastTimeOfAttemps: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LogicalMessage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastTimeOfAttemps: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastTimeOfAttemps: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.create(new LogicalMessage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LogicalMessage', () => {
        const returnedFromService = Object.assign(
          {
            ownerId: 1,
            documentId: 'BBBBBB',
            eventId: 'BBBBBB',
            directionId: 1,
            cmsId: 'BBBBBB',
            cmsDirectoryName: 'BBBBBB',
            transportPackageId: 1,
            receiptId: 'BBBBBB',
            messageTypeId: 1,
            responseCode: 1,
            attemps: 1,
            lastTimeOfAttemps: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastTimeOfAttemps: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LogicalMessage', () => {
        const returnedFromService = Object.assign(
          {
            ownerId: 1,
            documentId: 'BBBBBB',
            eventId: 'BBBBBB',
            directionId: 1,
            cmsId: 'BBBBBB',
            cmsDirectoryName: 'BBBBBB',
            transportPackageId: 1,
            receiptId: 'BBBBBB',
            messageTypeId: 1,
            responseCode: 1,
            attemps: 1,
            lastTimeOfAttemps: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastTimeOfAttemps: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LogicalMessage', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
