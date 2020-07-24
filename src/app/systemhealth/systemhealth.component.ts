import { Component, OnInit } from '@angular/core';
import { SystemHealthRecord } from '../systemhealthrecord';
import { SystemHealthService } from '../systemhealth.service';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';

@Component({
  selector: 'app-systemhealth',
  templateUrl: './systemhealth.component.html',
  styleUrls: ['./systemhealth.component.css']
})
export class SystemhealthComponent implements OnInit {

  healthRecord: SystemHealthRecord = null;
  availableRecords: string[] = [];
  
  constructor(
  private systemHealthService: SystemHealthService,
  private sanitizer: DomSanitizer) { }
  
  sectionOneShown: boolean = false;
  sectionTwoShown: boolean = false;
  sectionThreeShown: boolean = false;
  sectionFourShown: boolean = false;
  sectionFiveShown: boolean = false;
  sectionSixShown: boolean = false;
  sectionSevenShown: boolean = false;

  ngOnInit() {
  }
    retrieveAvailalble(): void {
            this.systemHealthService.getListOfReports().subscribe(response => {
 
        if (response.code == 0) {
            this.availableRecords = response.object;
           
        }
      });
    }
    performCheck():void {
    this.systemHealthService.buildReport().subscribe(response => {
 
        //if (response.code == 0) {
            this.healthRecord = response.object;
           
        //}
      });        
        
    }
    performCheckAndRebuild(): void {
           this.systemHealthService.buildReportAndFixData().subscribe(response => {
 
        //if (response.code == 0) {
            this.healthRecord = response.object;
           
        //}
      }); 
    }
    loadReport(reportId): void {
        this.systemHealthService.getReport(reportId).subscribe(response => {
 
        if (response.code == 0) {
            this.healthRecord = response.object;
           
        }
      });
    }
}
