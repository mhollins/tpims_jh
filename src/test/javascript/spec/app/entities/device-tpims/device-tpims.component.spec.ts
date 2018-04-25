/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { DeviceTpimsComponent } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims.component';
import { DeviceTpimsService } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims.service';
import { DeviceTpims } from '../../../../../../main/webapp/app/entities/device-tpims/device-tpims.model';

describe('Component Tests', () => {

    describe('DeviceTpims Management Component', () => {
        let comp: DeviceTpimsComponent;
        let fixture: ComponentFixture<DeviceTpimsComponent>;
        let service: DeviceTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DeviceTpimsComponent],
                providers: [
                    DeviceTpimsService
                ]
            })
            .overrideTemplate(DeviceTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviceTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviceTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DeviceTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.devices[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
