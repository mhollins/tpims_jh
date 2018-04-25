import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SiteTpims } from './site-tpims.model';
import { SiteTpimsService } from './site-tpims.service';

@Component({
    selector: 'jhi-site-tpims-detail',
    templateUrl: './site-tpims-detail.component.html'
})
export class SiteTpimsDetailComponent implements OnInit, OnDestroy {

    site: SiteTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private siteService: SiteTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSites();
    }

    load(id) {
        this.siteService.find(id)
            .subscribe((siteResponse: HttpResponse<SiteTpims>) => {
                this.site = siteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'siteListModification',
            (response) => this.load(this.site.id)
        );
    }
}
