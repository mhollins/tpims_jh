/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { DeviceTpimsDialogComponent } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims-dialog.component';
import { DeviceTpimsService } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims.service';
import { DeviceTpims } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims.model';
import { SiteTpimsService } from '../../../../../../main/webapp/app/entities/site-tpims';
import { LocationTpimsService } from '../../../../../../main/webapp/app/entities/location-tpims';

describe('Component Tests', () => {

    describe('DeviceTpims Management Dialog Component', () => {
        let comp: DeviceTpimsDialogComponent;
        let fixture: ComponentFixture<DeviceTpimsDialogComponent>;
        let service: DeviceTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DeviceTpimsDialogComponent],
                providers: [
                    SiteTpimsService,
                    LocationTpimsService,
                    DeviceTpimsService
                ]
            })
            .overrideTemplate(DeviceTpimsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviceTpimsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviceTpimsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DeviceTpims(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.device = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'deviceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DeviceTpims();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.device = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'deviceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
