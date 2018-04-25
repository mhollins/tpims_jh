import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ImagesTpims } from './images-tpims.model';
import { ImagesTpimsPopupService } from './images-tpims-popup.service';
import { ImagesTpimsService } from './images-tpims.service';
import { SiteTpims, SiteTpimsService } from '../site-tpims';

@Component({
    selector: 'jhi-images-tpims-dialog',
    templateUrl: './images-tpims-dialog.component.html'
})
export class ImagesTpimsDialogComponent implements OnInit {

    images: ImagesTpims;
    isSaving: boolean;

    sites: SiteTpims[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private imagesService: ImagesTpimsService,
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
        if (this.images.id !== undefined) {
            this.subscribeToSaveResponse(
                this.imagesService.update(this.images));
        } else {
            this.subscribeToSaveResponse(
                this.imagesService.create(this.images));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ImagesTpims>>) {
        result.subscribe((res: HttpResponse<ImagesTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ImagesTpims) {
        this.eventManager.broadcast({ name: 'imagesListModification', content: 'OK'});
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
    selector: 'jhi-images-tpims-popup',
    template: ''
})
export class ImagesTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imagesPopupService: ImagesTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imagesPopupService
                    .open(ImagesTpimsDialogComponent as Component, params['id']);
            } else {
                this.imagesPopupService
                    .open(ImagesTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
