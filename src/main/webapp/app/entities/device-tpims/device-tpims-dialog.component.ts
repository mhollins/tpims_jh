import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DeviceTpims } from './device-tpims.model';
import { DeviceTpimsPopupService } from './device-tpims-popup.service';
import { DeviceTpimsService } from './device-tpims.service';
import { SiteTpims, SiteTpimsService } from '../site-tpims';

@Component({
    selector: 'jhi-device-tpims-dialog',
    templateUrl: './device-tpims-dialog.component.html'
})
export class DeviceTpimsDialogComponent implements OnInit {

    device: DeviceTpims;
    isSaving: boolean;

    sites: SiteTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private deviceService: DeviceTpimsService,
        private siteService: SiteTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.siteService.query()
            .subscribe((res: HttpResponse<SiteTpims[]>) => { this.sites = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.device.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deviceService.update(this.device));
        } else {
            this.subscribeToSaveResponse(
                this.deviceService.create(this.device));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DeviceTpims>>) {
        result.subscribe((res: HttpResponse<DeviceTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DeviceTpims) {
        this.eventManager.broadcast({ name: 'deviceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSiteById(index: number, item: SiteTpims) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-device-tpims-popup',
    template: ''
})
export class DeviceTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private devicePopupService: DeviceTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.devicePopupService
                    .open(DeviceTpimsDialogComponent as Component, params['id']);
            } else {
                this.devicePopupService
                    .open(DeviceTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
