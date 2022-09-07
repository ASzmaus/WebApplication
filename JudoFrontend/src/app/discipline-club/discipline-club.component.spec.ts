import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisciplineClubComponent } from './discipline-club.component';

describe('DisciplineClubComponent', () => {
  let component: DisciplineClubComponent;
  let fixture: ComponentFixture<DisciplineClubComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisciplineClubComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DisciplineClubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
