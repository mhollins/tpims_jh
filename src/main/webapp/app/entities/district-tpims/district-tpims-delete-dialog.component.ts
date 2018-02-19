import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DistrictTpims } from './district-tpims.model';
import { DistrictTpimsPopupService } from './district-tpims-popup.service';
import { DistrictTpimsService } from './district-tpims.service';

@Component({
    selector: 'jhi-district-tpims-delete-dialog',
    templateUrl: './district-tpims-delete-dialog.component.html'
})
export class DistrictTpimsDeleteDialogComponent {

    district: DistrictTpims;

    constructor(
        private districtService: DistrictTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.districtService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'districtListModification',
                content: 'Deleted an district'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-district-tpims-delete-popup',
    template: ''
})
export class DistrictTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private districtPopupService: DistrictTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.districtPopupService
                .open(DistrictTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
