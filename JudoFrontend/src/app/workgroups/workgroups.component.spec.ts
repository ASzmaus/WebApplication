import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkGroupsComponent } from './workGroups.component';

describe('WorkGroupsComponent', () => {
  let component: WorkGroupsComponent;
  let fixture: ComponentFixture<WorkGroupsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WorkGroupsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
