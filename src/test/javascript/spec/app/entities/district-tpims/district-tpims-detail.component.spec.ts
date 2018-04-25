/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { DistrictTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/district-tpims/district-tpims-detail.component';
import { DistrictTpimsService } from '../../../../../../main/webapp/app/entities/district-tpims/district-tpims.service';
import { DistrictTpims } from '../../../../../../main/webapp/app/entities/district-tpims/district-tpims.model';

describe('Component Tests', () => {

    describe('DistrictTpims Management Detail Component', () => {
        let comp: DistrictTpimsDetailComponent;
        let fixture: ComponentFixture<DistrictTpimsDetailComponent>;
        let service: DistrictTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DistrictTpimsDetailComponent],
                providers: [
                    DistrictTpimsService
                ]
            })
            .overrideTemplate(DistrictTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DistrictTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DistrictTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DistrictTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.district).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
