import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TransportPackageService } from 'app/entities/transport-package/transport-package.service';
import { ITransportPackage, TransportPackage } from 'app/shared/model/transport-package.model';

describe('Service Tests', () => {
  describe('TransportPackage Service', () => {
    let injector: TestBed;
    let service: TransportPackageService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransportPackage;
    let expectedResult: ITransportPackage | ITransportPackage[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransportPackageService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TransportPackage(0, 0, 0, 'AAAAAAA', 0, 'AAAAAAA', 0, currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastTimeOfAttemps: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TransportPackage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastTimeOfAttemps: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastTimeOfAttemps: currentDate,
            createdAt: currentDate
          },
          returnedFromService
        );

        service.create(new TransportPackage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TransportPackage', () => {
        const returnedFromService = Object.assign(
          {
            transportPackageId: 1,
            directionId: 1,
            operatorId: 'BBBBBB',
            answerCode: 1,
            answerContent: 'BBBBBB',
            attemps: 1,
            lastTimeOfAttemps: currentDate.format(DATE_FORMAT),
            content: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastTimeOfAttemps: currentDate,
            createdAt: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TransportPackage', () => {
        const returnedFromService = Object.assign(
          {
            transportPackageId: 1,
            directionId: 1,
            operatorId: 'BBBBBB',
            answerCode: 1,
            answerContent: 'BBBBBB',
            attemps: 1,
            lastTimeOfAttemps: currentDate.format(DATE_FORMAT),
            content: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastTimeOfAttemps: currentDate,
            createdAt: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TransportPackage', () => {
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
