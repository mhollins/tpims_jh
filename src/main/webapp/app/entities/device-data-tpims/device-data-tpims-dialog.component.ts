import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DeviceDataTpims } from './device-data-tpims.model';
import { DeviceDataTpimsPopupService } from './device-data-tpims-popup.service';
import { DeviceDataTpimsService } from './device-data-tpims.service';
import { DeviceTpims, DeviceTpimsService } from '../device-tpims';

@Component({
    selector: 'jhi-device-data-tpims-dialog',
    templateUrl: './device-data-tpims-dialog.component.html'
})
export class DeviceDataTpimsDialogComponent implements OnInit {

    deviceData: DeviceDataTpims;
    isSaving: boolean;

    devices: DeviceTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private deviceDataService: DeviceDataTpimsService,
        private deviceService: DeviceTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.deviceService.query()
            .subscribe((res: HttpResponse<DeviceTpims[]>) => { this.devices = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.deviceData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deviceDataService.update(this.deviceData));
        } else {
            this.subscribeToSaveResponse(
                this.deviceDataService.create(this.deviceData));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DeviceDataTpims>>) {
        result.subscribe((res: HttpResponse<DeviceDataTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DeviceDataTpims) {
        this.eventManager.broadcast({ name: 'deviceDataListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDeviceById(index: number, item: DeviceTpims) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-device-data-tpims-popup',
    template: ''
})
export class DeviceDataTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deviceDataPopupService: DeviceDataTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.deviceDataPopupService
                    .open(DeviceDataTpimsDialogComponent as Component, params['id']);
            } else {
                this.deviceDataPopupService
                    .open(DeviceDataTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
