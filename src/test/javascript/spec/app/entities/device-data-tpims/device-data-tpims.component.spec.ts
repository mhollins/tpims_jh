/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { DeviceDataTpimsComponent } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims.component';
import { DeviceDataTpimsService } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims.service';
import { DeviceDataTpims } from '../../../../../../main/webapp/app/entities/device-data-tpims/device-data-tpims.model';

describe('Component Tests', () => {

    describe('DeviceDataTpims Management Component', () => {
        let comp: DeviceDataTpimsComponent;
        let fixture: ComponentFixture<DeviceDataTpimsComponent>;
        let service: DeviceDataTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DeviceDataTpimsComponent],
                providers: [
                    DeviceDataTpimsService
                ]
            })
            .overrideTemplate(DeviceDataTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviceDataTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviceDataTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DeviceDataTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.deviceData[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
