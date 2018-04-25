/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { HistoricSiteDataTpimsDialogComponent } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims-dialog.component';
import { HistoricSiteDataTpimsService } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims.service';
import { HistoricSiteDataTpims } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims.model';
import { SiteTpimsService } from '../../../../../../main/webapp/app/entities/site-tpims';

describe('Component Tests', () => {

    describe('HistoricSiteDataTpims Management Dialog Component', () => {
        let comp: HistoricSiteDataTpimsDialogComponent;
        let fixture: ComponentFixture<HistoricSiteDataTpimsDialogComponent>;
        let service: HistoricSiteDataTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [HistoricSiteDataTpimsDialogComponent],
                providers: [
                    SiteTpimsService,
                    HistoricSiteDataTpimsService
                ]
            })
            .overrideTemplate(HistoricSiteDataTpimsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HistoricSiteDataTpimsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoricSiteDataTpimsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HistoricSiteDataTpims(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.historicSiteData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'historicSiteDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HistoricSiteDataTpims();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.historicSiteData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'historicSiteDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
