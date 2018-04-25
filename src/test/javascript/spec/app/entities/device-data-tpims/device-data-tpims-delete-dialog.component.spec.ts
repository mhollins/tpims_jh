/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { DeviceDataTpimsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims-delete-dialog.component';
import { DeviceDataTpimsService } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims.service';

describe('Component Tests', () => {

    describe('DeviceDataTpims Management Delete Component', () => {
        let comp: DeviceDataTpimsDeleteDialogComponent;
        let fixture: ComponentFixture<DeviceDataTpimsDeleteDialogComponent>;
        let service: DeviceDataTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DeviceDataTpimsDeleteDialogComponent],
                providers: [
                    DeviceDataTpimsService
                ]
            })
            .overrideTemplate(DeviceDataTpimsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviceDataTpimsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviceDataTpimsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
