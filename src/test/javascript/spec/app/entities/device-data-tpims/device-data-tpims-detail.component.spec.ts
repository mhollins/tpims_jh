/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { DeviceDataTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims-detail.component';
import { DeviceDataTpimsService } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims.service';
import { DeviceDataTpims } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims.model';

describe('Component Tests', () => {

    describe('DeviceDataTpims Management Detail Component', () => {
        let comp: DeviceDataTpimsDetailComponent;
        let fixture: ComponentFixture<DeviceDataTpimsDetailComponent>;
        let service: DeviceDataTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DeviceDataTpimsDetailComponent],
                providers: [
                    DeviceDataTpimsService
                ]
            })
            .overrideTemplate(DeviceDataTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviceDataTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviceDataTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DeviceDataTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.deviceData).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
