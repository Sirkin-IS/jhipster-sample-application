import { Moment } from 'moment';
import { IOperators } from 'app/shared/model/operators.model';
import { ILogicalMessage } from 'app/shared/model/logical-message.model';
import { ITransportPackageRepeat } from 'app/shared/model/transport-package-repeat.model';
import { IBadIncomingTransportPackage } from 'app/shared/model/bad-incoming-transport-package.model';

export interface ITransportPackage {
  id?: number;
  transportPackageId?: number;
  directionId?: number;
  operatorId?: string;
  answerCode?: number;
  answerContent?: string;
  attemps?: number;
  lastTimeOfAttemps?: Moment;
  content?: string;
  createdAt?: Moment;
  operatorIds?: IOperators[];
  transportPackageId?: ILogicalMessage;
  id?: ITransportPackageRepeat;
  id?: IBadIncomingTransportPackage;
}

export class TransportPackage implements ITransportPackage {
  constructor(
    public id?: number,
    public transportPackageId?: number,
    public directionId?: number,
    public operatorId?: string,
    public answerCode?: number,
    public answerContent?: string,
    public attemps?: number,
    public lastTimeOfAttemps?: Moment,
    public content?: string,
    public createdAt?: Moment,
    public operatorIds?: IOperators[],
    public transportPackageId?: ILogicalMessage,
    public id?: ITransportPackageRepeat,
    public id?: IBadIncomingTransportPackage
  ) {}
}
