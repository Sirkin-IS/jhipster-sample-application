import { ILogicalMessage } from 'app/shared/model/logical-message.model';

export interface IMessageType {
  id?: number;
  name?: string;
  id?: ILogicalMessage;
}

export class MessageType implements IMessageType {
  constructor(public id?: number, public name?: string, public id?: ILogicalMessage) {}
}
