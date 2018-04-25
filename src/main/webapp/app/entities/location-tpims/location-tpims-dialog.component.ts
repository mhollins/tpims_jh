import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LocationTpims } from './location-tpims.model';
import { LocationTpimsPopupService } from './location-tpims-popup.service';
import { LocationTpimsService } from './location-tpims.service';
import { CountyTpims, CountyTpimsService } from '../county-tpims';

@Component({
    selector: 'jhi-location-tpims-dialog',
    templateUrl: './location-tpims-dialog.component.html'
})
export class LocationTpimsDialogComponent implements OnInit {

    location: LocationTpims;
    isSaving: boolean;

    counties: CountyTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private locationService: LocationTpimsService,
        private countyService: CountyTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.countyService.query()
            .subscribe((res: HttpResponse<CountyTpims[]>) => { this.counties = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationService.update(this.location));
        } else {
            this.subscribeToSaveResponse(
                this.locationService.create(this.location));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LocationTpims>>) {
        result.subscribe((res: HttpResponse<LocationTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LocationTpims) {
        this.eventManager.broadcast({ name: 'locationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCountyById(index: number, item: CountyTpims) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-location-tpims-popup',
    template: ''
})
export class LocationTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationPopupService: LocationTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.locationPopupService
                    .open(LocationTpimsDialogComponent as Component, params['id']);
            } else {
                this.locationPopupService
                    .open(LocationTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
