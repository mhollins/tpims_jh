import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HistoricSiteDataTpims } from './historic-site-data-tpims.model';
import { HistoricSiteDataTpimsService } from './historic-site-data-tpims.service';

@Component({
    selector: 'jhi-historic-site-data-tpims-detail',
    templateUrl: './historic-site-data-tpims-detail.component.html'
})
export class HistoricSiteDataTpimsDetailComponent implements OnInit, OnDestroy {

    historicSiteData: HistoricSiteDataTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private historicSiteDataService: HistoricSiteDataTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHistoricSiteData();
    }

    load(id) {
        this.historicSiteDataService.find(id)
            .subscribe((historicSiteDataResponse: HttpResponse<HistoricSiteDataTpims>) => {
                this.historicSiteData = historicSiteDataResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHistoricSiteData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'historicSiteDataListModification',
            (response) => this.load(this.historicSiteData.id)
        );
    }
}
