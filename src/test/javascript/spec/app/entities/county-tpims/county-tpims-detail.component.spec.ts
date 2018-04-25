/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { CountyTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/county-tpims/county-tpims-detail.component';
import { CountyTpimsService } from '../../../../../../main/webapp/app/entities/county-tpims/county-tpims.service';
import { CountyTpims } from '../../../../../../main/webapp/app/entities/county-tpims/county-tpims.model';

describe('Component Tests', () => {

    describe('CountyTpims Management Detail Component', () => {
        let comp: CountyTpimsDetailComponent;
        let fixture: ComponentFixture<CountyTpimsDetailComponent>;
        let service: CountyTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [CountyTpimsDetailComponent],
                providers: [
                    CountyTpimsService
                ]
            })
            .overrideTemplate(CountyTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CountyTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountyTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CountyTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.county).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
