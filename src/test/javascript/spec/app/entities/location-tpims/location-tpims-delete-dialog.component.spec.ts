/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { LocationTpimsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/location-tpims/location-tpims-delete-dialog.component';
import { LocationTpimsService } from '../../../../../../main/webapp/app/entities/location-tpims/location-tpims.service';

describe('Component Tests', () => {

    describe('LocationTpims Management Delete Component', () => {
        let comp: LocationTpimsDeleteDialogComponent;
        let fixture: ComponentFixture<LocationTpimsDeleteDialogComponent>;
        let service: LocationTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [LocationTpimsDeleteDialogComponent],
                providers: [
                    LocationTpimsService
                ]
            })
            .overrideTemplate(LocationTpimsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationTpimsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationTpimsService);
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
