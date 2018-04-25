import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LocationTpims } from './location-tpims.model';
import { LocationTpimsPopupService } from './location-tpims-popup.service';
import { LocationTpimsService } from './location-tpims.service';

@Component({
    selector: 'jhi-location-tpims-delete-dialog',
    templateUrl: './location-tpims-delete-dialog.component.html'
})
export class LocationTpimsDeleteDialogComponent {

    location: LocationTpims;

    constructor(
        private locationService: LocationTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.locationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'locationListModification',
                content: 'Deleted an location'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-location-tpims-delete-popup',
    template: ''
})
export class LocationTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationPopupService: LocationTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.locationPopupService
                .open(LocationTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
