/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { LocationTpimsComponent } from '../../../../../../main/webapp/app/entities/location-tpims/location-tpims.component';
import { LocationTpimsService } from '../../../../../../main/webapp/app/entities/location-tpims/location-tpims.service';
import { LocationTpims } from '../../../../../../main/webapp/app/entities/location-tpims/location-tpims.model';

describe('Component Tests', () => {

    describe('LocationTpims Management Component', () => {
        let comp: LocationTpimsComponent;
        let fixture: ComponentFixture<LocationTpimsComponent>;
        let service: LocationTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [LocationTpimsComponent],
                providers: [
                    LocationTpimsService
                ]
            })
            .overrideTemplate(LocationTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LocationTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.locations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
