/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { DistrictTpimsComponent } from '../../../../../../main/webapp/app/entities/district-tpims/district-tpims.component';
import { DistrictTpimsService } from '../../../../../../main/webapp/app/entities/district-tpims/district-tpims.service';
import { DistrictTpims } from '../../../../../../main/webapp/app/entities/district-tpims/district-tpims.model';

describe('Component Tests', () => {

    describe('DistrictTpims Management Component', () => {
        let comp: DistrictTpimsComponent;
        let fixture: ComponentFixture<DistrictTpimsComponent>;
        let service: DistrictTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [DistrictTpimsComponent],
                providers: [
                    DistrictTpimsService
                ]
            })
            .overrideTemplate(DistrictTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DistrictTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DistrictTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DistrictTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.districts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
