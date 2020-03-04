import { Moment } from 'moment';
import { ITransportPackage } from 'app/shared/model/transport-package.model';

export interface IBadIncomingTransportPackage {
  id?: number;
  transportPackageId?: number;
  date?: Moment;
  contentContentType?: string;
  content?: any;
  answerCode?: number;
  transportPackageIds?: ITransportPackage[];
}

export class BadIncomingTransportPackage implements IBadIncomingTransportPackage {
  constructor(
    public id?: number,
    public transportPackageId?: number,
    public date?: Moment,
    public contentContentType?: string,
    public content?: any,
    public answerCode?: number,
    public transportPackageIds?: ITransportPackage[]
  ) {}
}
