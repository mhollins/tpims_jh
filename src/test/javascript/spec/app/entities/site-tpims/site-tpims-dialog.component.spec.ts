/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { SiteTpimsDialogComponent } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims-dialog.component';
import { SiteTpimsService } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims.service';
import { SiteTpims } from '../../../../../../main/webapp/app/entities/site-tpims/site-tpims.model';
import { LocationTpimsService } from '../../../../../../main/webapp/app/entities/location-tpims';
import { SiteStatusTpimsService } from '../../../../../../main/webapp/app/entities/site-status-tpims';

describe('Component Tests', () => {

    describe('SiteTpims Management Dialog Component', () => {
        let comp: SiteTpimsDialogComponent;
        let fixture: ComponentFixture<SiteTpimsDialogComponent>;
        let service: SiteTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [SiteTpimsDialogComponent],
                providers: [
                    LocationTpimsService,
                    SiteStatusTpimsService,
                    SiteTpimsService
                ]
            })
            .overrideTemplate(SiteTpimsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SiteTpimsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiteTpimsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SiteTpims(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.site = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'siteListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SiteTpims();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.site = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'siteListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
