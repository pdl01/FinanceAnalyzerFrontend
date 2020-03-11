import { Component, OnInit,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';

import { CompanyService } from '../company.service';

import { Company } from '../company';
import { RestResponse } from '../restresponse';

@Component({
  selector: 'app-stockdetails',
  templateUrl: './stockdetails.component.html',
  styleUrls: ['./stockdetails.component.css']
})
export class StockdetailsComponent implements OnInit {

  constructor(private route: ActivatedRoute,
  private companyService: CompanyService,
  private location: Location,
  private sanitizer: DomSanitizer    ) { }
  private company: Company;
  private stockHistories: StockHistory[] = [];

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.loadCompany(id);
  }

  loadCompany(id): void {
     this.companyService.getCompanyById(id).subscribe(response => {
 
        if (response.code == 0) {
            this.company = response.object;
        }
      });
  }
  loadStocks(id): void {
  }
}
