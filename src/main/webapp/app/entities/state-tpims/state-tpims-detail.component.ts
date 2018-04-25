import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { StateTpims } from './state-tpims.model';
import { StateTpimsService } from './state-tpims.service';

@Component({
    selector: 'jhi-state-tpims-detail',
    templateUrl: './state-tpims-detail.component.html'
})
export class StateTpimsDetailComponent implements OnInit, OnDestroy {

    state: StateTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private stateService: StateTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStates();
    }

    load(id) {
        this.stateService.find(id)
            .subscribe((stateResponse: HttpResponse<StateTpims>) => {
                this.state = stateResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'stateListModification',
            (response) => this.load(this.state.id)
        );
    }
}
