/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { DeviceDataTpimsDialogComponent } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims-dialog.component';
import { DeviceDataTpimsService } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims.service';
import { DeviceDataTpims } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims.model';
import { DeviceTpimsService } from '../../../../../../main/webapp/app/entities/device-tpims';

describe('Component Tests', () => {

    describe('DeviceDataTpims Management Dialog Component', () => {
        let comp: DeviceDataTpimsDialogComponent;
        let fixture: ComponentFixture<DeviceDataTpimsDialogComponent>;
        let service: DeviceDataTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DeviceDataTpimsDialogComponent],
                providers: [
                    DeviceTpimsService,
                    DeviceDataTpimsService
                ]
            })
            .overrideTemplate(DeviceDataTpimsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviceDataTpimsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviceDataTpimsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DeviceDataTpims(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.deviceData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'deviceDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DeviceDataTpims();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.deviceData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'deviceDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
