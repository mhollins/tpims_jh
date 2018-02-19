/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { AmenitiesTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims-detail.component';
import { AmenitiesTpimsService } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims.service';
import { AmenitiesTpims } from '../../../../../../main/webapp/app/entities/amenities-tpims/amenities-tpims.model';

describe('Component Tests', () => {

    describe('AmenitiesTpims Management Detail Component', () => {
        let comp: AmenitiesTpimsDetailComponent;
        let fixture: ComponentFixture<AmenitiesTpimsDetailComponent>;
        let service: AmenitiesTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [AmenitiesTpimsDetailComponent],
                providers: [
                    AmenitiesTpimsService
                ]
            })
            .overrideTemplate(AmenitiesTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmenitiesTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmenitiesTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AmenitiesTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.amenities).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
