/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { HistoricSiteDataTpimsComponent } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims.component';
import { HistoricSiteDataTpimsService } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims.service';
import { HistoricSiteDataTpims } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims.model';

describe('Component Tests', () => {

    describe('HistoricSiteDataTpims Management Component', () => {
        let comp: HistoricSiteDataTpimsComponent;
        let fixture: ComponentFixture<HistoricSiteDataTpimsComponent>;
        let service: HistoricSiteDataTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [HistoricSiteDataTpimsComponent],
                providers: [
                    HistoricSiteDataTpimsService
                ]
            })
            .overrideTemplate(HistoricSiteDataTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HistoricSiteDataTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoricSiteDataTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HistoricSiteDataTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.historicSiteData[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
