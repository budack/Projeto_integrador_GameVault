import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalhesJogoComponent } from './detalhes-jogo.component';

describe('DetalhesJogoComponent', () => {
  let component: DetalhesJogoComponent;
  let fixture: ComponentFixture<DetalhesJogoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalhesJogoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalhesJogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
