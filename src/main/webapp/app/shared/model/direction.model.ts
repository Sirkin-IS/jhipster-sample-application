import { ILogicalMessage } from 'app/shared/model/logical-message.model';

export interface IDirection {
  id?: number;
  name?: string;
  id?: ILogicalMessage;
}

export class Direction implements IDirection {
  constructor(public id?: number, public name?: string, public id?: ILogicalMessage) {}
}
