import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SiteStatusTpims } from './site-status-tpims.model';
import { SiteStatusTpimsPopupService } from './site-status-tpims-popup.service';
import { SiteStatusTpimsService } from './site-status-tpims.service';

@Component({
    selector: 'jhi-site-status-tpims-delete-dialog',
    templateUrl: './site-status-tpims-delete-dialog.component.html'
})
export class SiteStatusTpimsDeleteDialogComponent {

    siteStatus: SiteStatusTpims;

    constructor(
        private siteStatusService: SiteStatusTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.siteStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'siteStatusListModification',
                content: 'Deleted an siteStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-site-status-tpims-delete-popup',
    template: ''
})
export class SiteStatusTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private siteStatusPopupService: SiteStatusTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.siteStatusPopupService
                .open(SiteStatusTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
