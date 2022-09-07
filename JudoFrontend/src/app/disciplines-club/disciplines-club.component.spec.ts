import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisciplinesClubComponent } from './disciplines-club.component';

describe('DisciplinesClubComponent', () => {
  let component: DisciplinesClubComponent;
  let fixture: ComponentFixture<DisciplinesClubComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisciplinesClubComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DisciplinesClubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
