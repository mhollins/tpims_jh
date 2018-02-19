import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SiteTpims } from './site-tpims.model';
import { SiteTpimsPopupService } from './site-tpims-popup.service';
import { SiteTpimsService } from './site-tpims.service';
import { LocationTpims, LocationTpimsService } from '../location-tpims';
import { SiteStatusTpims, SiteStatusTpimsService } from '../site-status-tpims';

@Component({
    selector: 'jhi-site-tpims-dialog',
    templateUrl: './site-tpims-dialog.component.html'
})
export class SiteTpimsDialogComponent implements OnInit {

    site: SiteTpims;
    isSaving: boolean;

    locations: LocationTpims[];

    sitestatuses: SiteStatusTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private siteService: SiteTpimsService,
        private locationService: LocationTpimsService,
        private siteStatusService: SiteStatusTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService
            .query({filter: 'site-is-null'})
            .subscribe((res: HttpResponse<LocationTpims[]>) => {
                if (!this.site.locationId) {
                    this.locations = res.body;
                } else {
                    this.locationService
                        .find(this.site.locationId)
                        .subscribe((subRes: HttpResponse<LocationTpims>) => {
                            this.locations = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.siteStatusService.query()
            .subscribe((res: HttpResponse<SiteStatusTpims[]>) => { this.sitestatuses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.site.id !== undefined) {
            this.subscribeToSaveResponse(
                this.siteService.update(this.site));
        } else {
            this.subscribeToSaveResponse(
                this.siteService.create(this.site));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SiteTpims>>) {
        result.subscribe((res: HttpResponse<SiteTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SiteTpims) {
        this.eventManager.broadcast({ name: 'siteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: LocationTpims) {
        return item.id;
    }

    trackSiteStatusById(index: number, item: SiteStatusTpims) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-site-tpims-popup',
    template: ''
})
export class SiteTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sitePopupService: SiteTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sitePopupService
                    .open(SiteTpimsDialogComponent as Component, params['id']);
            } else {
                this.sitePopupService
                    .open(SiteTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
