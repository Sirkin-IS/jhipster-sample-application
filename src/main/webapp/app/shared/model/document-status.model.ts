import { IDocumentHistory } from 'app/shared/model/document-history.model';

export interface IDocumentStatus {
  id?: number;
  name?: string;
  id?: IDocumentHistory;
}

export class DocumentStatus implements IDocumentStatus {
  constructor(public id?: number, public name?: string, public id?: IDocumentHistory) {}
}
