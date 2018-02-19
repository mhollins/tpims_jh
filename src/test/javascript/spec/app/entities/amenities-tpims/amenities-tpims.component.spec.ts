/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { AmenitiesTpimsComponent } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims.component';
import { AmenitiesTpimsService } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims.service';
import { AmenitiesTpims } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims.model';

describe('Component Tests', () => {

    describe('AmenitiesTpims Management Component', () => {
        let comp: AmenitiesTpimsComponent;
        let fixture: ComponentFixture<AmenitiesTpimsComponent>;
        let service: AmenitiesTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [AmenitiesTpimsComponent],
                providers: [
                    AmenitiesTpimsService
                ]
            })
            .overrideTemplate(AmenitiesTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmenitiesTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmenitiesTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AmenitiesTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.amenities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
