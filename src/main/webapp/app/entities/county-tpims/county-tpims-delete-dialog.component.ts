import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CountyTpims } from './county-tpims.model';
import { CountyTpimsPopupService } from './county-tpims-popup.service';
import { CountyTpimsService } from './county-tpims.service';

@Component({
    selector: 'jhi-county-tpims-delete-dialog',
    templateUrl: './county-tpims-delete-dialog.component.html'
})
export class CountyTpimsDeleteDialogComponent {

    county: CountyTpims;

    constructor(
        private countyService: CountyTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.countyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'countyListModification',
                content: 'Deleted an county'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-county-tpims-delete-popup',
    template: ''
})
export class CountyTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countyPopupService: CountyTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.countyPopupService
                .open(CountyTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
