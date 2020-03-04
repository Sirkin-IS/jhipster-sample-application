import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TransportPackageRepeatService } from 'app/entities/transport-package-repeat/transport-package-repeat.service';
import { ITransportPackageRepeat, TransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';

describe('Service Tests', () => {
  describe('TransportPackageRepeat Service', () => {
    let injector: TestBed;
    let service: TransportPackageRepeatService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransportPackageRepeat;
    let expectedResult: ITransportPackageRepeat | ITransportPackageRepeat[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransportPackageRepeatService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TransportPackageRepeat(0, 0, 0, currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TransportPackageRepeat', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate
          },
          returnedFromService
        );

        service.create(new TransportPackageRepeat()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TransportPackageRepeat', () => {
        const returnedFromService = Object.assign(
          {
            transportPackageId: 1,
            repeatNum: 1,
            createdAt: currentDate.format(DATE_FORMAT),
            answerCode: 1,
            answerContent: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TransportPackageRepeat', () => {
        const returnedFromService = Object.assign(
          {
            transportPackageId: 1,
            repeatNum: 1,
            createdAt: currentDate.format(DATE_FORMAT),
            answerCode: 1,
            answerContent: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a TransportPackageRepeat', () => {
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
