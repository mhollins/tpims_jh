/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { LocationTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/location-tpims/location-tpims-detail.component';
import { LocationTpimsService } from '../../../../../../main/webapp/app/entities/location-tpims/location-tpims.service';
import { LocationTpims } from '../../../../../../main/webapp/app/entities/location-tpims/location-tpims.model';

describe('Component Tests', () => {

    describe('LocationTpims Management Detail Component', () => {
        let comp: LocationTpimsDetailComponent;
        let fixture: ComponentFixture<LocationTpimsDetailComponent>;
        let service: LocationTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [LocationTpimsDetailComponent],
                providers: [
                    LocationTpimsService
                ]
            })
            .overrideTemplate(LocationTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LocationTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.location).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
