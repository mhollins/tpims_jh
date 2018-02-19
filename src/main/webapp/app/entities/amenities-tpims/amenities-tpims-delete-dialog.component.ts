import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AmenitiesTpims } from './amenities-tpims.model';
import { AmenitiesTpimsPopupService } from './amenities-tpims-popup.service';
import { AmenitiesTpimsService } from './amenities-tpims.service';

@Component({
    selector: 'jhi-amenities-tpims-delete-dialog',
    templateUrl: './amenities-tpims-delete-dialog.component.html'
})
export class AmenitiesTpimsDeleteDialogComponent {

    amenities: AmenitiesTpims;

    constructor(
        private amenitiesService: AmenitiesTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.amenitiesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'amenitiesListModification',
                content: 'Deleted an amenities'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-amenities-tpims-delete-popup',
    template: ''
})
export class AmenitiesTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private amenitiesPopupService: AmenitiesTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.amenitiesPopupService
                .open(AmenitiesTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
