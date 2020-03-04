import { Moment } from 'moment';
import { IHbaseFile } from 'app/shared/model/hbase-file.model';
import { ILogicalMessage } from 'app/shared/model/logical-message.model';

export interface ICMS {
  id?: number;
  fileId?: string;
  messageCount?: number;
  createdAt?: Moment;
  fileIds?: IHbaseFile[];
  id?: ILogicalMessage;
}

export class CMS implements ICMS {
  constructor(
    public id?: number,
    public fileId?: string,
    public messageCount?: number,
    public createdAt?: Moment,
    public fileIds?: IHbaseFile[],
    public id?: ILogicalMessage
  ) {}
}
