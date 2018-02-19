/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { SiteTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims-detail.component';
import { SiteTpimsService } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims.service';
import { SiteTpims } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims.model';

describe('Component Tests', () => {

    describe('SiteTpims Management Detail Component', () => {
        let comp: SiteTpimsDetailComponent;
        let fixture: ComponentFixture<SiteTpimsDetailComponent>;
        let service: SiteTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [SiteTpimsDetailComponent],
                providers: [
                    SiteTpimsService
                ]
            })
            .overrideTemplate(SiteTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SiteTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiteTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SiteTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.site).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
