import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SiteStatusTpims } from './site-status-tpims.model';
import { SiteStatusTpimsPopupService } from './site-status-tpims-popup.service';
import { SiteStatusTpimsService } from './site-status-tpims.service';
import { SiteTpims, SiteTpimsService } from '../site-tpims';

@Component({
    selector: 'jhi-site-status-tpims-dialog',
    templateUrl: './site-status-tpims-dialog.component.html'
})
export class SiteStatusTpimsDialogComponent implements OnInit {

    siteStatus: SiteStatusTpims;
    isSaving: boolean;

    sites: SiteTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private siteStatusService: SiteStatusTpimsService,
        private siteService: SiteTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.siteService
            .query({filter: 'status-is-null'})
            .subscribe((res: HttpResponse<SiteTpims[]>) => {
                if (!this.siteStatus.siteId) {
                    this.sites = res.body;
                } else {
                    this.siteService
                        .find(this.siteStatus.siteId)
                        .subscribe((subRes: HttpResponse<SiteTpims>) => {
                            this.sites = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.siteStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.siteStatusService.update(this.siteStatus));
        } else {
            this.subscribeToSaveResponse(
                this.siteStatusService.create(this.siteStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SiteStatusTpims>>) {
        result.subscribe((res: HttpResponse<SiteStatusTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SiteStatusTpims) {
        this.eventManager.broadcast({ name: 'siteStatusListModification', content: 'OK'});
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
    selector: 'jhi-site-status-tpims-popup',
    template: ''
})
export class SiteStatusTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private siteStatusPopupService: SiteStatusTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.siteStatusPopupService
                    .open(SiteStatusTpimsDialogComponent as Component, params['id']);
            } else {
                this.siteStatusPopupService
                    .open(SiteStatusTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
