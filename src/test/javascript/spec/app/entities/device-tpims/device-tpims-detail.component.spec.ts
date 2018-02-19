/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { DeviceTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims-detail.component';
import { DeviceTpimsService } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims.service';
import { DeviceTpims } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims.model';

describe('Component Tests', () => {

    describe('DeviceTpims Management Detail Component', () => {
        let comp: DeviceTpimsDetailComponent;
        let fixture: ComponentFixture<DeviceTpimsDetailComponent>;
        let service: DeviceTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DeviceTpimsDetailComponent],
                providers: [
                    DeviceTpimsService
                ]
            })
            .overrideTemplate(DeviceTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviceTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviceTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DeviceTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.device).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
