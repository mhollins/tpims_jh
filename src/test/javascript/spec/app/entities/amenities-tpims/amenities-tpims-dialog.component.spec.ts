/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { AmenitiesTpimsDialogComponent } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims-dialog.component';
import { AmenitiesTpimsService } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims.service';
import { AmenitiesTpims } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims.model';
import { SiteTpimsService } from '../../../../../../main/webapp/app/entities/site-tpims';

describe('Component Tests', () => {

    describe('AmenitiesTpims Management Dialog Component', () => {
        let comp: AmenitiesTpimsDialogComponent;
        let fixture: ComponentFixture<AmenitiesTpimsDialogComponent>;
        let service: AmenitiesTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [AmenitiesTpimsDialogComponent],
                providers: [
                    SiteTpimsService,
                    AmenitiesTpimsService
                ]
            })
            .overrideTemplate(AmenitiesTpimsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmenitiesTpimsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmenitiesTpimsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AmenitiesTpims(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.amenities = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'amenitiesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AmenitiesTpims();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.amenities = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'amenitiesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
