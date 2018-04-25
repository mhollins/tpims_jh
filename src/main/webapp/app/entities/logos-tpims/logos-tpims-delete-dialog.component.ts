import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LogosTpims } from './logos-tpims.model';
import { LogosTpimsPopupService } from './logos-tpims-popup.service';
import { LogosTpimsService } from './logos-tpims.service';

@Component({
    selector: 'jhi-logos-tpims-delete-dialog',
    templateUrl: './logos-tpims-delete-dialog.component.html'
})
export class LogosTpimsDeleteDialogComponent {

    logos: LogosTpims;

    constructor(
        private logosService: LogosTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.logosService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'logosListModification',
                content: 'Deleted an logos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-logos-tpims-delete-popup',
    template: ''
})
export class LogosTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logosPopupService: LogosTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.logosPopupService
                .open(LogosTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
