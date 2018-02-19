/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { SiteStatusTpimsDialogComponent } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims-dialog.component';
import { SiteStatusTpimsService } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims.service';
import { SiteStatusTpims } from '../../../../../../main/webapp/app/entities/site-status-tpims/site-status-tpims.model';
import { SiteTpimsService } from '../../../../../../main/webapp/app/entities/site-tpims';

describe('Component Tests', () => {

    describe('SiteStatusTpims Management Dialog Component', () => {
        let comp: SiteStatusTpimsDialogComponent;
        let fixture: ComponentFixture<SiteStatusTpimsDialogComponent>;
        let service: SiteStatusTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [SiteStatusTpimsDialogComponent],
                providers: [
                    SiteTpimsService,
                    SiteStatusTpimsService
                ]
            })
            .overrideTemplate(SiteStatusTpimsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SiteStatusTpimsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiteStatusTpimsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SiteStatusTpims(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.siteStatus = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'siteStatusListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SiteStatusTpims();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.siteStatus = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'siteStatusListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
