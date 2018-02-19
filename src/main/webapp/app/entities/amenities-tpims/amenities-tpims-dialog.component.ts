import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AmenitiesTpims } from './amenities-tpims.model';
import { AmenitiesTpimsPopupService } from './amenities-tpims-popup.service';
import { AmenitiesTpimsService } from './amenities-tpims.service';
import { SiteTpims, SiteTpimsService } from '../site-tpims';

@Component({
    selector: 'jhi-amenities-tpims-dialog',
    templateUrl: './amenities-tpims-dialog.component.html'
})
export class AmenitiesTpimsDialogComponent implements OnInit {

    amenities: AmenitiesTpims;
    isSaving: boolean;

    sites: SiteTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private amenitiesService: AmenitiesTpimsService,
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
        if (this.amenities.id !== undefined) {
            this.subscribeToSaveResponse(
                this.amenitiesService.update(this.amenities));
        } else {
            this.subscribeToSaveResponse(
                this.amenitiesService.create(this.amenities));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AmenitiesTpims>>) {
        result.subscribe((res: HttpResponse<AmenitiesTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AmenitiesTpims) {
        this.eventManager.broadcast({ name: 'amenitiesListModification', content: 'OK'});
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
    selector: 'jhi-amenities-tpims-popup',
    template: ''
})
export class AmenitiesTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private amenitiesPopupService: AmenitiesTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.amenitiesPopupService
                    .open(AmenitiesTpimsDialogComponent as Component, params['id']);
            } else {
                this.amenitiesPopupService
                    .open(AmenitiesTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
