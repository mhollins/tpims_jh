/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { SiteStatusTpimsComponent } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims.component';
import { SiteStatusTpimsService } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims.service';
import { SiteStatusTpims } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims.model';

describe('Component Tests', () => {

    describe('SiteStatusTpims Management Component', () => {
        let comp: SiteStatusTpimsComponent;
        let fixture: ComponentFixture<SiteStatusTpimsComponent>;
        let service: SiteStatusTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [SiteStatusTpimsComponent],
                providers: [
                    SiteStatusTpimsService
                ]
            })
            .overrideTemplate(SiteStatusTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SiteStatusTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiteStatusTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SiteStatusTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.siteStatuses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
