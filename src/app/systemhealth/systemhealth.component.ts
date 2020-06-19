import { Component, OnInit } from '@angular/core';
import { SystemHealthRecord } from '../systemhealthrecord';

@Component({
  selector: 'app-systemhealth',
  templateUrl: './systemhealth.component.html',
  styleUrls: ['./systemhealth.component.css']
})
export class SystemhealthComponent implements OnInit {

  healthRecord: SystemHealthRecord = null;
  availableRecords: string[] = [];
  
  constructor() { }

  ngOnInit() {
  }
    retrieveAvailalble(): void {
    }
    performCheck():void {
    }
    performCheckAndRebuild(): void {
    }
}
