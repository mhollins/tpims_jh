import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DeviceTpims } from './device-tpims.model';
import { DeviceTpimsService } from './device-tpims.service';

@Component({
    selector: 'jhi-device-tpims-detail',
    templateUrl: './device-tpims-detail.component.html'
})
export class DeviceTpimsDetailComponent implements OnInit, OnDestroy {

    device: DeviceTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private deviceService: DeviceTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDevices();
    }

    load(id) {
        this.deviceService.find(id)
            .subscribe((deviceResponse: HttpResponse<DeviceTpims>) => {
                this.device = deviceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDevices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'deviceListModification',
            (response) => this.load(this.device.id)
        );
    }
}
