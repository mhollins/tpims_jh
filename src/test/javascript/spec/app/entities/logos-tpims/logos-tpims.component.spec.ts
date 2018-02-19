/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { LogosTpimsComponent } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims.component';
import { LogosTpimsService } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims.service';
import { LogosTpims } from '../../../../../../main/webapp/app/entities/logos-tpims/logos-tpims.model';

describe('Component Tests', () => {

    describe('LogosTpims Management Component', () => {
        let comp: LogosTpimsComponent;
        let fixture: ComponentFixture<LogosTpimsComponent>;
        let service: LogosTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [LogosTpimsComponent],
                providers: [
                    LogosTpimsService
                ]
            })
            .overrideTemplate(LogosTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogosTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogosTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LogosTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.logos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
