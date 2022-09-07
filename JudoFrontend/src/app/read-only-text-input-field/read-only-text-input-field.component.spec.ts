import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadOnlyTextInputFieldComponent } from './read-only-text-input-field.component';

describe('ReadOnlyTextInputFieldComponent', () => {
  let component: ReadOnlyTextInputFieldComponent;
  let fixture: ComponentFixture<ReadOnlyTextInputFieldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReadOnlyTextInputFieldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadOnlyTextInputFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
