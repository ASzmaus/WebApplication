import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TextInputFieldCellComponent } from './text-input-field-cell.component';

describe('TextInputFieldCellComponent', () => {
  let component: TextInputFieldCellComponent;
  let fixture: ComponentFixture<TextInputFieldCellComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TextInputFieldCellComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TextInputFieldCellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
