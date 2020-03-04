import { ITransportPackage } from 'app/shared/model/transport-package.model';

export interface IOperators {
  id?: number;
  operatorId?: string;
  url?: string;
  secondUrl?: string;
  title?: string;
  alias?: string;
  id?: ITransportPackage;
}

export class Operators implements IOperators {
  constructor(
    public id?: number,
    public operatorId?: string,
    public url?: string,
    public secondUrl?: string,
    public title?: string,
    public alias?: string,
    public id?: ITransportPackage
  ) {}
}
