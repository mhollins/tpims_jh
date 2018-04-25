/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { LogosTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims-detail.component';
import { LogosTpimsService } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims.service';
import { LogosTpims } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims.model';

describe('Component Tests', () => {

    describe('LogosTpims Management Detail Component', () => {
        let comp: LogosTpimsDetailComponent;
        let fixture: ComponentFixture<LogosTpimsDetailComponent>;
        let service: LogosTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [LogosTpimsDetailComponent],
                providers: [
                    LogosTpimsService
                ]
            })
            .overrideTemplate(LogosTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogosTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogosTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LogosTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.logos).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
