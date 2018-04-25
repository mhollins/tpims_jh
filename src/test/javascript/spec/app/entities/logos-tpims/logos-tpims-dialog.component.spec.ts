/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TpimsTestModule } from '../../../test.module';
import { LogosTpimsDialogComponent } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims-dialog.component';
import { LogosTpimsService } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims.service';
import { LogosTpims } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims.model';
import { SiteTpimsService } from '../../../../../../main/webapp/app/entities/site-tpims';

describe('Component Tests', () => {

    describe('LogosTpims Management Dialog Component', () => {
        let comp: LogosTpimsDialogComponent;
        let fixture: ComponentFixture<LogosTpimsDialogComponent>;
        let service: LogosTpimsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [LogosTpimsDialogComponent],
                providers: [
                    SiteTpimsService,
                    LogosTpimsService
                ]
            })
            .overrideTemplate(LogosTpimsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogosTpimsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogosTpimsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LogosTpims(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.logos = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'logosListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LogosTpims();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.logos = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'logosListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
