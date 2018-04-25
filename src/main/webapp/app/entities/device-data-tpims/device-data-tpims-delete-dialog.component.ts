import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DeviceDataTpims } from './device-data-tpims.model';
import { DeviceDataTpimsPopupService } from './device-data-tpims-popup.service';
import { DeviceDataTpimsService } from './device-data-tpims.service';

@Component({
    selector: 'jhi-device-data-tpims-delete-dialog',
    templateUrl: './device-data-tpims-delete-dialog.component.html'
})
export class DeviceDataTpimsDeleteDialogComponent {

    deviceData: DeviceDataTpims;

    constructor(
        private deviceDataService: DeviceDataTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deviceDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deviceDataListModification',
                content: 'Deleted an deviceData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-device-data-tpims-delete-popup',
    template: ''
})
export class DeviceDataTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deviceDataPopupService: DeviceDataTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.deviceDataPopupService
                .open(DeviceDataTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
