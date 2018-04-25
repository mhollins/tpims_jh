/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { SiteStatusTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims-detail.component';
import { SiteStatusTpimsService } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims.service';
import { SiteStatusTpims } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims.model';

describe('Component Tests', () => {

    describe('SiteStatusTpims Management Detail Component', () => {
        let comp: SiteStatusTpimsDetailComponent;
        let fixture: ComponentFixture<SiteStatusTpimsDetailComponent>;
        let service: SiteStatusTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [SiteStatusTpimsDetailComponent],
                providers: [
                    SiteStatusTpimsService
                ]
            })
            .overrideTemplate(SiteStatusTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SiteStatusTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiteStatusTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SiteStatusTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.siteStatus).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
