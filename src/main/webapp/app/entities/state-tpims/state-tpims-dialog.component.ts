import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StateTpims } from './state-tpims.model';
import { StateTpimsPopupService } from './state-tpims-popup.service';
import { StateTpimsService } from './state-tpims.service';

@Component({
    selector: 'jhi-state-tpims-dialog',
    templateUrl: './state-tpims-dialog.component.html'
})
export class StateTpimsDialogComponent implements OnInit {

    state: StateTpims;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private stateService: StateTpimsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.state.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stateService.update(this.state));
        } else {
            this.subscribeToSaveResponse(
                this.stateService.create(this.state));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<StateTpims>>) {
        result.subscribe((res: HttpResponse<StateTpims>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: StateTpims) {
        this.eventManager.broadcast({ name: 'stateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-state-tpims-popup',
    template: ''
})
export class StateTpimsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statePopupService: StateTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.statePopupService
                    .open(StateTpimsDialogComponent as Component, params['id']);
            } else {
                this.statePopupService
                    .open(StateTpimsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
