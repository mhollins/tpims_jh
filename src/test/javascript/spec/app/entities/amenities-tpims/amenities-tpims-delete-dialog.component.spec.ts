/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { AmenitiesTpimsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims-delete-dialog.component';
import { AmenitiesTpimsService } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims.service';

describe('Component Tests', () => {

    describe('AmenitiesTpims Management Delete Component', () => {
        let comp: AmenitiesTpimsDeleteDialogComponent;
        let fixture: ComponentFixture<AmenitiesTpimsDeleteDialogComponent>;
        let service: AmenitiesTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [AmenitiesTpimsDeleteDialogComponent],
                providers: [
                    AmenitiesTpimsService
                ]
            })
            .overrideTemplate(AmenitiesTpimsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmenitiesTpimsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmenitiesTpimsService);
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
