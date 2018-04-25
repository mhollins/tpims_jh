import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AmenitiesTpims } from './amenities-tpims.model';
import { AmenitiesTpimsService } from './amenities-tpims.service';

@Component({
    selector: 'jhi-amenities-tpims-detail',
    templateUrl: './amenities-tpims-detail.component.html'
})
export class AmenitiesTpimsDetailComponent implements OnInit, OnDestroy {

    amenities: AmenitiesTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private amenitiesService: AmenitiesTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAmenities();
    }

    load(id) {
        this.amenitiesService.find(id)
            .subscribe((amenitiesResponse: HttpResponse<AmenitiesTpims>) => {
                this.amenities = amenitiesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAmenities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'amenitiesListModification',
            (response) => this.load(this.amenities.id)
        );
    }
}
