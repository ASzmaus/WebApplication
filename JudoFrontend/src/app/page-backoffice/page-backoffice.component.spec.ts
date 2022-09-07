import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PageBackofficeComponent } from './page-backoffice.component';

describe('PageBackofficeComponent', () => {
  let component: PageBackofficeComponent;
  let fixture: ComponentFixture<PageBackofficeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PageBackofficeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PageBackofficeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
