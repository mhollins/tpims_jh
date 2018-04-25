import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DeviceDataTpims } from './device-data-tpims.model';
import { DeviceDataTpimsService } from './device-data-tpims.service';

@Component({
    selector: 'jhi-device-data-tpims-detail',
    templateUrl: './device-data-tpims-detail.component.html'
})
export class DeviceDataTpimsDetailComponent implements OnInit, OnDestroy {

    deviceData: DeviceDataTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private deviceDataService: DeviceDataTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDeviceData();
    }

    load(id) {
        this.deviceDataService.find(id)
            .subscribe((deviceDataResponse: HttpResponse<DeviceDataTpims>) => {
                this.deviceData = deviceDataResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDeviceData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'deviceDataListModification',
            (response) => this.load(this.deviceData.id)
        );
    }
}
