import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHbaseFile } from 'app/shared/model/hbase-file.model';

@Component({
  selector: 'jhi-hbase-file-detail',
  templateUrl: './hbase-file-detail.component.html'
})
export class HbaseFileDetailComponent implements OnInit {
  hbaseFile: IHbaseFile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hbaseFile }) => (this.hbaseFile = hbaseFile));
  }

  previousState(): void {
    window.history.back();
  }
}
