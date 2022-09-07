import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemainderEnterPasswordComponent } from './remainder-enter-password.component';

describe('RemainderEnterPasswordComponent', () => {
  let component: RemainderEnterPasswordComponent;
  let fixture: ComponentFixture<RemainderEnterPasswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemainderEnterPasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemainderEnterPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
