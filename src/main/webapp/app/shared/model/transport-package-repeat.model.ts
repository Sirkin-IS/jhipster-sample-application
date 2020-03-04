import { Moment } from 'moment';
import { ITransportPackage } from 'app/shared/model/transport-package.model';

export interface ITransportPackageRepeat {
  id?: number;
  transportPackageId?: number;
  repeatNum?: number;
  createdAt?: Moment;
  answerCode?: number;
  answerContent?: string;
  transportPackageIds?: ITransportPackage[];
}

export class TransportPackageRepeat implements ITransportPackageRepeat {
  constructor(
    public id?: number,
    public transportPackageId?: number,
    public repeatNum?: number,
    public createdAt?: Moment,
    public answerCode?: number,
    public answerContent?: string,
    public transportPackageIds?: ITransportPackage[]
  ) {}
}
