import { Moment } from 'moment';
import { ILogicalMessage } from 'app/shared/model/logical-message.model';

export interface IReceipt {
  id?: number;
  cmsId?: number;
  receiptTypeId?: number;
  receiptDate?: Moment;
  createdAt?: Moment;
  id?: ILogicalMessage;
}

export class Receipt implements IReceipt {
  constructor(
    public id?: number,
    public cmsId?: number,
    public receiptTypeId?: number,
    public receiptDate?: Moment,
    public createdAt?: Moment,
    public id?: ILogicalMessage
  ) {}
}
