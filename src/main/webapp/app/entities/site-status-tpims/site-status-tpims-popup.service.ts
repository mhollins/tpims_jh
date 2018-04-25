import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { SiteStatusTpims } from './site-status-tpims.model';
import { SiteStatusTpimsService } from './site-status-tpims.service';

@Injectable()
export class SiteStatusTpimsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private siteStatusService: SiteStatusTpimsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.siteStatusService.find(id)
                    .subscribe((siteStatusResponse: HttpResponse<SiteStatusTpims>) => {
                        const siteStatus: SiteStatusTpims = siteStatusResponse.body;
                        siteStatus.lastDeviceUpdate = this.datePipe
                            .transform(siteStatus.lastDeviceUpdate, 'yyyy-MM-ddTHH:mm:ss');
                        siteStatus.lastOperatorUpdate = this.datePipe
                            .transform(siteStatus.lastOperatorUpdate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.siteStatusModalRef(component, siteStatus);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.siteStatusModalRef(component, new SiteStatusTpims());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    siteStatusModalRef(component: Component, siteStatus: SiteStatusTpims): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.siteStatus = siteStatus;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
