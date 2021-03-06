/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { HistoricSiteDataTpimsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims-delete-dialog.component';
import { HistoricSiteDataTpimsService } from '../../../../../../main/webapp/app/entities/historic-site-data-tpims/historic-site-data-tpims.service';

describe('Component Tests', () => {

    describe('HistoricSiteDataTpims Management Delete Component', () => {
        let comp: HistoricSiteDataTpimsDeleteDialogComponent;
        let fixture: ComponentFixture<HistoricSiteDataTpimsDeleteDialogComponent>;
        let service: HistoricSiteDataTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [HistoricSiteDataTpimsDeleteDialogComponent],
                providers: [
                    HistoricSiteDataTpimsService
                ]
            })
            .overrideTemplate(HistoricSiteDataTpimsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HistoricSiteDataTpimsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoricSiteDataTpimsService);
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
