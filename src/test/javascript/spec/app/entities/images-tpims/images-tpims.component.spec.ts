/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpimsTestModule } from '../../../test.module';
import { ImagesTpimsComponent } from '../../../../../../main/webapp/app/entities/images-tpims/images-tpims.component';
import { ImagesTpimsService } from '../../../../../../main/webapp/app/entities/images-tpims/images-tpims.service';
import { ImagesTpims } from '../../../../../../main/webapp/app/entities/images-tpims/images-tpims.model';

describe('Component Tests', () => {

    describe('ImagesTpims Management Component', () => {
        let comp: ImagesTpimsComponent;
        let fixture: ComponentFixture<ImagesTpimsComponent>;
        let service: ImagesTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [ImagesTpimsComponent],
                providers: [
                    ImagesTpimsService
                ]
            })
            .overrideTemplate(ImagesTpimsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImagesTpimsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagesTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ImagesTpims(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.images[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
