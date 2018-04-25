/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { StateTpimsComponent } from '../../../../../../main/webapp/app/entities/state-tpims/state-tpims.component';
import { StateTpimsService } from '../../../../../../main/webapp/app/entities/state-tpims/state-tpims.service';
import { StateTpims } from '../../../../../../main/webapp/app/entities/state-tpims/state-tpims.model';

describe('Component Tests', () => {

    describe('StateTpims Management Component', () => {
        let comp: StateTpimsComponent;
        let fixture: ComponentFixture<StateTpimsComponent>;
        let service: StateTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [StateTpimsComponent],
                providers: [
                    StateTpimsService
                ]
            })
            .overrideTemplate(StateTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StateTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StateTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new StateTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.states[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
