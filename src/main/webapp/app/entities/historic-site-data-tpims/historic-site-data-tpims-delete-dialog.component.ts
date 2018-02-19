import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HistoricSiteDataTpims } from './historic-site-data-tpims.model';
import { HistoricSiteDataTpimsPopupService } from './historic-site-data-tpims-popup.service';
import { HistoricSiteDataTpimsService } from './historic-site-data-tpims.service';

@Component({
    selector: 'jhi-historic-site-data-tpims-delete-dialog',
    templateUrl: './historic-site-data-tpims-delete-dialog.component.html'
})
export class HistoricSiteDataTpimsDeleteDialogComponent {

    historicSiteData: HistoricSiteDataTpims;

    constructor(
        private historicSiteDataService: HistoricSiteDataTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.historicSiteDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'historicSiteDataListModification',
                content: 'Deleted an historicSiteData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-historic-site-data-tpims-delete-popup',
    template: ''
})
export class HistoricSiteDataTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historicSiteDataPopupService: HistoricSiteDataTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.historicSiteDataPopupService
                .open(HistoricSiteDataTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
