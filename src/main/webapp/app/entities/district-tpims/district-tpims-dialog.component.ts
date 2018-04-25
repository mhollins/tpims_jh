import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DistrictTpims } from './district-tpims.model';
import { DistrictTpimsPopupService } from './district-tpims-popup.service';
import { DistrictTpimsService } from './district-tpims.service';
import { StateTpims, StateTpimsService } from '../state-tpims';

@Component({
    selector: 'jhi-district-tpims-dialog',
    templateUrl: './district-tpims-dialog.component.html'
})
export class DistrictTpimsDialogComponent implements OnInit {

    district: DistrictTpims;
    isSaving: boolean;

    states: StateTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private districtService: DistrictTpimsService,
        private stateService: StateTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.stateService.query()
            .subscribe((res: HttpResponse<StateTpims[]>) => { this.states = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.district.id !== undefined) {
            this.subscribeToSaveResponse(
                this.districtService.update(this.district));
        } else {
            this.subscribeToSaveResponse(
                this.districtService.create(this.district));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DistrictTpims>>) {
        result.subscribe((res: HttpResponse<DistrictTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DistrictTpims) {
        this.eventManager.broadcast({ name: 'districtListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackStateById(index: number, item: StateTpims) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-district-tpims-popup',
    template: ''
})
export class DistrictTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private districtPopupService: DistrictTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.districtPopupService
                    .open(DistrictTpimsDialogComponent as Component, params['id']);
            } else {
                this.districtPopupService
                    .open(DistrictTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
