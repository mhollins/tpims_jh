/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TpimsTestModule } from '../../../test.module';
import { ImagesTpimsDetailComponent } from '../../../../../../main/webapp/app/entities/images-tpims/images-tpims-detail.component';
import { ImagesTpimsService } from '../../../../../../main/webapp/app/entities/images-tpims/images-tpims.service';
import { ImagesTpims } from '../../../../../../main/webapp/app/entities/images-tpims/images-tpims.model';

describe('Component Tests', () => {

    describe('ImagesTpims Management Detail Component', () => {
        let comp: ImagesTpimsDetailComponent;
        let fixture: ComponentFixture<ImagesTpimsDetailComponent>;
        let service: ImagesTpimsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TpimsTestModule],
                declarations: [ImagesTpimsDetailComponent],
                providers: [
                    ImagesTpimsService
                ]
            })
            .overrideTemplate(ImagesTpimsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImagesTpimsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagesTpimsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ImagesTpims(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.images).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
