import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemhealthComponent } from './systemhealth.component';

describe('SystemhealthComponent', () => {
  let component: SystemhealthComponent;
  let fixture: ComponentFixture<SystemhealthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SystemhealthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemhealthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
