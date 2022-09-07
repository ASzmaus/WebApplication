import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadOnlyTextareaComponent } from './read-only-textarea.component';

describe('ReadOnlyTextareaComponent', () => {
  let component: ReadOnlyTextareaComponent;
  let fixture: ComponentFixture<ReadOnlyTextareaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReadOnlyTextareaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadOnlyTextareaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
