/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { HistoricSiteDataTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims-detail.component';
import { HistoricSiteDataTpimsService } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims.service';
import { HistoricSiteDataTpims } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims.model';

describe('Component Tests', () => {

    describe('HistoricSiteDataTpims Management Detail Component', () => {
        let comp: HistoricSiteDataTpimsDetailComponent;
        let fixture: ComponentFixture<HistoricSiteDataTpimsDetailComponent>;
        let service: HistoricSiteDataTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [HistoricSiteDataTpimsDetailComponent],
                providers: [
                    HistoricSiteDataTpimsService
                ]
            })
            .overrideTemplate(HistoricSiteDataTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HistoricSiteDataTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoricSiteDataTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HistoricSiteDataTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.historicSiteData).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
