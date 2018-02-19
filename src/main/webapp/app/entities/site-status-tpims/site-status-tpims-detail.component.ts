import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SiteStatusTpims } from './site-status-tpims.model';
import { SiteStatusTpimsService } from './site-status-tpims.service';

@Component({
    selector: 'jhi-site-status-tpims-detail',
    templateUrl: './site-status-tpims-detail.component.html'
})
export class SiteStatusTpimsDetailComponent implements OnInit, OnDestroy {

    siteStatus: SiteStatusTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private siteStatusService: SiteStatusTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSiteStatuses();
    }

    load(id) {
        this.siteStatusService.find(id)
            .subscribe((siteStatusResponse: HttpResponse<SiteStatusTpims>) => {
                this.siteStatus = siteStatusResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSiteStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'siteStatusListModification',
            (response) => this.load(this.siteStatus.id)
        );
    }
}
