import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ReceiptService } from 'app/entities/receipt/receipt.service';
import { IReceipt, Receipt } from 'app/shared/model/receipt.model';

describe('Service Tests', () => {
  describe('Receipt Service', () => {
    let injector: TestBed;
    let service: ReceiptService;
    let httpMock: HttpTestingController;
    let elemDefault: IReceipt;
    let expectedResult: IReceipt | IReceipt[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ReceiptService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Receipt(0, 0, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            receiptDate: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Receipt', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            receiptDate: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            receiptDate: currentDate,
            createdAt: currentDate
          },
          returnedFromService
        );

        service.create(new Receipt()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Receipt', () => {
        const returnedFromService = Object.assign(
          {
            cmsId: 1,
            receiptTypeId: 1,
            receiptDate: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            receiptDate: currentDate,
            createdAt: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Receipt', () => {
        const returnedFromService = Object.assign(
          {
            cmsId: 1,
            receiptTypeId: 1,
            receiptDate: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            receiptDate: currentDate,
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

      it('should delete a Receipt', () => {
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
