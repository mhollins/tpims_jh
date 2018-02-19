import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DistrictTpims } from './district-tpims.model';
import { DistrictTpimsService } from './district-tpims.service';

@Component({
    selector: 'jhi-district-tpims-detail',
    templateUrl: './district-tpims-detail.component.html'
})
export class DistrictTpimsDetailComponent implements OnInit, OnDestroy {

    district: DistrictTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private districtService: DistrictTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDistricts();
    }

    load(id) {
        this.districtService.find(id)
            .subscribe((districtResponse: HttpResponse<DistrictTpims>) => {
                this.district = districtResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDistricts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'districtListModification',
            (response) => this.load(this.district.id)
        );
    }
}
