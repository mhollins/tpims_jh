/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { CountyTpimsComponent } from '../../../../../../main/webapp/app/entities/county-tpims/county-tpims.component';
import { CountyTpimsService } from '../../../../../../main/webapp/app/entities/county-tpims/county-tpims.service';
import { CountyTpims } from '../../../../../../main/webapp/app/entities/county-tpims/county-tpims.model';

describe('Component Tests', () => {

    describe('CountyTpims Management Component', () => {
        let comp: CountyTpimsComponent;
        let fixture: ComponentFixture<CountyTpimsComponent>;
        let service: CountyTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [CountyTpimsComponent],
                providers: [
                    CountyTpimsService
                ]
            })
            .overrideTemplate(CountyTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CountyTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountyTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CountyTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.counties[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
