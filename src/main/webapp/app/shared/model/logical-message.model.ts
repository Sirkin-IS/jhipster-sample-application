import { Moment } from 'moment';
import { ITransportPackage } from 'app/shared/model/transport-package.model';
import { IReceipt } from 'app/shared/model/receipt.model';
import { IDirection } from 'app/shared/model/direction.model';
import { ICMS } from 'app/shared/model/cms.model';
import { IMessageType } from 'app/shared/model/message-type.model';

export interface ILogicalMessage {
  id?: number;
  ownerId?: number;
  documentId?: string;
  eventId?: string;
  directionId?: number;
  cmsId?: string;
  cmsDirectoryName?: string;
  transportPackageId?: number;
  receiptId?: string;
  messageTypeId?: number;
  responseCode?: number;
  attemps?: number;
  lastTimeOfAttemps?: Moment;
  createdAt?: Moment;
  updatedAt?: Moment;
  transportPackageId?: ITransportPackage;
  receiptId?: IReceipt;
  directionIds?: IDirection[];
  cmsIds?: ICMS[];
  messageTypeIds?: IMessageType[];
}

export class LogicalMessage implements ILogicalMessage {
  constructor(
    public id?: number,
    public ownerId?: number,
    public documentId?: string,
    public eventId?: string,
    public directionId?: number,
    public cmsId?: string,
    public cmsDirectoryName?: string,
    public transportPackageId?: number,
    public receiptId?: string,
    public messageTypeId?: number,
    public responseCode?: number,
    public attemps?: number,
    public lastTimeOfAttemps?: Moment,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public transportPackageId?: ITransportPackage,
    public receiptId?: IReceipt,
    public directionIds?: IDirection[],
    public cmsIds?: ICMS[],
    public messageTypeIds?: IMessageType[]
  ) {}
}
