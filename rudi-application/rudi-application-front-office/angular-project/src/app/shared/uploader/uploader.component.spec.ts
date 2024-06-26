import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UploaderComponent} from './uploader.component';

describe('UploaderComponent', () => {
    let component: UploaderComponent<unknown>;
    let fixture: ComponentFixture<UploaderComponent<unknown>>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [UploaderComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(UploaderComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
