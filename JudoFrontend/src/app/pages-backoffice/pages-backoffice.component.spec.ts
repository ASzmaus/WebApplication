import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PagesBackofficeComponent } from './pages-backoffice.component';

describe('PagesBackofficeComponent', () => {
  let component: PagesBackofficeComponent;
  let fixture: ComponentFixture<PagesBackofficeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PagesBackofficeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PagesBackofficeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
