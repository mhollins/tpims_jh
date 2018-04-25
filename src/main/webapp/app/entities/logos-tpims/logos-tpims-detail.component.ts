import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LogosTpims } from './logos-tpims.model';
import { LogosTpimsService } from './logos-tpims.service';

@Component({
    selector: 'jhi-logos-tpims-detail',
    templateUrl: './logos-tpims-detail.component.html'
})
export class LogosTpimsDetailComponent implements OnInit, OnDestroy {

    logos: LogosTpims;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private logosService: LogosTpimsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLogos();
    }

    load(id) {
        this.logosService.find(id)
            .subscribe((logosResponse: HttpResponse<LogosTpims>) => {
                this.logos = logosResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLogos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'logosListModification',
            (response) => this.load(this.logos.id)
        );
    }
}
