import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BadIncomingTransportPackageService } from 'app/entities/bad-incoming-transport-package/bad-incoming-transport-package.service';
import { IBadIncomingTransportPackage, BadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';

describe('Service Tests', () => {
  describe('BadIncomingTransportPackage Service', () => {
    let injector: TestBed;
    let service: BadIncomingTransportPackageService;
    let httpMock: HttpTestingController;
    let elemDefault: IBadIncomingTransportPackage;
    let expectedResult: IBadIncomingTransportPackage | IBadIncomingTransportPackage[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BadIncomingTransportPackageService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BadIncomingTransportPackage(0, 0, currentDate, 'image/png', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BadIncomingTransportPackage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        service.create(new BadIncomingTransportPackage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BadIncomingTransportPackage', () => {
        const returnedFromService = Object.assign(
          {
            transportPackageId: 1,
            date: currentDate.format(DATE_FORMAT),
            content: 'BBBBBB',
            answerCode: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BadIncomingTransportPackage', () => {
        const returnedFromService = Object.assign(
          {
            transportPackageId: 1,
            date: currentDate.format(DATE_FORMAT),
            content: 'BBBBBB',
            answerCode: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BadIncomingTransportPackage', () => {
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
