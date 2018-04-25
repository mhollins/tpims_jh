/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { ImagesTpimsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/images-tpims/images-tpims-delete-dialog.component';
import { ImagesTpimsService } from '../../../../../../main/webapp/app/entities/images-tpims/images-tpims.service';

describe('Component Tests', () => {

    describe('ImagesTpims Management Delete Component', () => {
        let comp: ImagesTpimsDeleteDialogComponent;
        let fixture: ComponentFixture<ImagesTpimsDeleteDialogComponent>;
        let service: ImagesTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [ImagesTpimsDeleteDialogComponent],
                providers: [
                    ImagesTpimsService
                ]
            })
            .overrideTemplate(ImagesTpimsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImagesTpimsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagesTpimsService);
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
