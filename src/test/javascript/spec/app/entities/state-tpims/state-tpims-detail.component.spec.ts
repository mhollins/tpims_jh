/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { StateTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/state-tpims/state-tpims-detail.component';
import { StateTpimsService } from '../../../../../../main/webapp/app/entities/state-tpims/state-tpims.service';
import { StateTpims } from '../../../../../../main/webapp/app/entities/state-tpims/state-tpims.model';

describe('Component Tests', () => {

    describe('StateTpims Management Detail Component', () => {
        let comp: StateTpimsDetailComponent;
        let fixture: ComponentFixture<StateTpimsDetailComponent>;
        let service: StateTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [StateTpimsDetailComponent],
                providers: [
                    StateTpimsService
                ]
            })
            .overrideTemplate(StateTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StateTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StateTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new StateTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.state).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
