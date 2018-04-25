import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DeviceTpims } from './device-tpims.model';
import { DeviceTpimsPopupService } from './device-tpims-popup.service';
import { DeviceTpimsService } from './device-tpims.service';

@Component({
    selector: 'jhi-device-tpims-delete-dialog',
    templateUrl: './device-tpims-delete-dialog.component.html'
})
export class DeviceTpimsDeleteDialogComponent {

    device: DeviceTpims;

    constructor(
        private deviceService: DeviceTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deviceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deviceListModification',
                content: 'Deleted an device'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-device-tpims-delete-popup',
    template: ''
})
export class DeviceTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private devicePopupService: DeviceTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.devicePopupService
                .open(DeviceTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
