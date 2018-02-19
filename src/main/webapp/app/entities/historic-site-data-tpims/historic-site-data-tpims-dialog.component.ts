import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HistoricSiteDataTpims } from './historic-site-data-tpims.model';
import { HistoricSiteDataTpimsPopupService } from './historic-site-data-tpims-popup.service';
import { HistoricSiteDataTpimsService } from './historic-site-data-tpims.service';
import { SiteTpims, SiteTpimsService } from '../site-tpims';

@Component({
    selector: 'jhi-historic-site-data-tpims-dialog',
    templateUrl: './historic-site-data-tpims-dialog.component.html'
})
export class HistoricSiteDataTpimsDialogComponent implements OnInit {

    historicSiteData: HistoricSiteDataTpims;
    isSaving: boolean;

    sites: SiteTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private historicSiteDataService: HistoricSiteDataTpimsService,
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
        if (this.historicSiteData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.historicSiteDataService.update(this.historicSiteData));
        } else {
            this.subscribeToSaveResponse(
                this.historicSiteDataService.create(this.historicSiteData));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HistoricSiteDataTpims>>) {
        result.subscribe((res: HttpResponse<HistoricSiteDataTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HistoricSiteDataTpims) {
        this.eventManager.broadcast({ name: 'historicSiteDataListModification', content: 'OK'});
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
    selector: 'jhi-historic-site-data-tpims-popup',
    template: ''
})
export class HistoricSiteDataTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historicSiteDataPopupService: HistoricSiteDataTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.historicSiteDataPopupService
                    .open(HistoricSiteDataTpimsDialogComponent as Component, params['id']);
            } else {
                this.historicSiteDataPopupService
                    .open(HistoricSiteDataTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
