import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CountyTpims } from './county-tpims.model';
import { CountyTpimsService } from './county-tpims.service';

@Component({
    selector: 'jhi-county-tpims-detail',
    templateUrl: './county-tpims-detail.component.html'
})
export class CountyTpimsDetailComponent implements OnInit, OnDestroy {

    county: CountyTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private countyService: CountyTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCounties();
    }

    load(id) {
        this.countyService.find(id)
            .subscribe((countyResponse: HttpResponse<CountyTpims>) => {
                this.county = countyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCounties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'countyListModification',
            (response) => this.load(this.county.id)
        );
    }
}
