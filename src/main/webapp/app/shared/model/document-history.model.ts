import { Moment } from 'moment';
import { IDocumentStatus } from 'app/shared/model/document-status.model';

export interface IDocumentHistory {
  id?: number;
  ownerId?: number;
  directionId?: number;
  documentId?: number;
  statusId?: number;
  date?: Moment;
  statusIds?: IDocumentStatus[];
}

export class DocumentHistory implements IDocumentHistory {
  constructor(
    public id?: number,
    public ownerId?: number,
    public directionId?: number,
    public documentId?: number,
    public statusId?: number,
    public date?: Moment,
    public statusIds?: IDocumentStatus[]
  ) {}
}
