import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LocationTpims } from './location-tpims.model';
import { LocationTpimsService } from './location-tpims.service';

@Component({
    selector: 'jhi-location-tpims-detail',
    templateUrl: './location-tpims-detail.component.html'
})
export class LocationTpimsDetailComponent implements OnInit, OnDestroy {

    location: LocationTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private locationService: LocationTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLocations();
    }

    load(id) {
        this.locationService.find(id)
            .subscribe((locationResponse: HttpResponse<LocationTpims>) => {
                this.location = locationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLocations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'locationListModification',
            (response) => this.load(this.location.id)
        );
    }
}
