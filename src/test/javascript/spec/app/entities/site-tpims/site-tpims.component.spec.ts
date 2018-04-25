/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { SiteTpimsComponent } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims.component';
import { SiteTpimsService } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims.service';
import { SiteTpims } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims.model';

describe('Component Tests', () => {

    describe('SiteTpims Management Component', () => {
        let comp: SiteTpimsComponent;
        let fixture: ComponentFixture<SiteTpimsComponent>;
        let service: SiteTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [SiteTpimsComponent],
                providers: [
                    SiteTpimsService
                ]
            })
            .overrideTemplate(SiteTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SiteTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiteTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SiteTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sites[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
