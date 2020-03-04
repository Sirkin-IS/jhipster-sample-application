import { ICMS } from 'app/shared/model/cms.model';

export interface IHbaseFile {
  id?: number;
  fileId?: number;
  content?: string;
  fileId?: ICMS;
}

export class HbaseFile implements IHbaseFile {
  constructor(public id?: number, public fileId?: number, public content?: string, public fileId?: ICMS) {}
}
