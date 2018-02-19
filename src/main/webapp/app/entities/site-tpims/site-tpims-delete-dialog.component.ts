import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SiteTpims } from './site-tpims.model';
import { SiteTpimsPopupService } from './site-tpims-popup.service';
import { SiteTpimsService } from './site-tpims.service';

@Component({
    selector: 'jhi-site-tpims-delete-dialog',
    templateUrl: './site-tpims-delete-dialog.component.html'
})
export class SiteTpimsDeleteDialogComponent {

    site: SiteTpims;

    constructor(
        private siteService: SiteTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.siteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'siteListModification',
                content: 'Deleted an site'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-site-tpims-delete-popup',
    template: ''
})
export class SiteTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sitePopupService: SiteTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sitePopupService
                .open(SiteTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
