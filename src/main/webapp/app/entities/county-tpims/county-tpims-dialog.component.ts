import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CountyTpims } from './county-tpims.model';
import { CountyTpimsPopupService } from './county-tpims-popup.service';
import { CountyTpimsService } from './county-tpims.service';
import { DistrictTpims, DistrictTpimsService } from '../district-tpims';

@Component({
    selector: 'jhi-county-tpims-dialog',
    templateUrl: './county-tpims-dialog.component.html'
})
export class CountyTpimsDialogComponent implements OnInit {

    county: CountyTpims;
    isSaving: boolean;

    districts: DistrictTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private countyService: CountyTpimsService,
        private districtService: DistrictTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.districtService.query()
            .subscribe((res: HttpResponse<DistrictTpims[]>) => { this.districts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.county.id !== undefined) {
            this.subscribeToSaveResponse(
                this.countyService.update(this.county));
        } else {
            this.subscribeToSaveResponse(
                this.countyService.create(this.county));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CountyTpims>>) {
        result.subscribe((res: HttpResponse<CountyTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CountyTpims) {
        this.eventManager.broadcast({ name: 'countyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDistrictById(index: number, item: DistrictTpims) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-county-tpims-popup',
    template: ''
})
export class CountyTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countyPopupService: CountyTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.countyPopupService
                    .open(CountyTpimsDialogComponent as Component, params['id']);
            } else {
                this.countyPopupService
                    .open(CountyTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
