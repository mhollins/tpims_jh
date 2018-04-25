import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LogosTpims } from './logos-tpims.model';
import { LogosTpimsPopupService } from './logos-tpims-popup.service';
import { LogosTpimsService } from './logos-tpims.service';
import { SiteTpims, SiteTpimsService } from '../site-tpims';

@Component({
    selector: 'jhi-logos-tpims-dialog',
    templateUrl: './logos-tpims-dialog.component.html'
})
export class LogosTpimsDialogComponent implements OnInit {

    logos: LogosTpims;
    isSaving: boolean;

    sites: SiteTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private logosService: LogosTpimsService,
        private siteService: SiteTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.siteService.query()
            .subscribe((res: HttpResponse<SiteTpims[]>) => { this.sites = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.logos.id !== undefined) {
            this.subscribeToSaveResponse(
                this.logosService.update(this.logos));
        } else {
            this.subscribeToSaveResponse(
                this.logosService.create(this.logos));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LogosTpims>>) {
        result.subscribe((res: HttpResponse<LogosTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LogosTpims) {
        this.eventManager.broadcast({ name: 'logosListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSiteById(index: number, item: SiteTpims) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-logos-tpims-popup',
    template: ''
})
export class LogosTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logosPopupService: LogosTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.logosPopupService
                    .open(LogosTpimsDialogComponent as Component, params['id']);
            } else {
                this.logosPopupService
                    .open(LogosTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
