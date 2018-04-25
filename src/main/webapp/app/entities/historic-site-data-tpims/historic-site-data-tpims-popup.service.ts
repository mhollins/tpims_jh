import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { HistoricSiteDataTpims } from './historic-site-data-tpims.model';
import { HistoricSiteDataTpimsService } from './historic-site-data-tpims.service';

@Injectable()
export class HistoricSiteDataTpimsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private historicSiteDataService: HistoricSiteDataTpimsService

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
                this.historicSiteDataService.find(id)
                    .subscribe((historicSiteDataResponse: HttpResponse<HistoricSiteDataTpims>) => {
                        const historicSiteData: HistoricSiteDataTpims = historicSiteDataResponse.body;
                        historicSiteData.timeStamp = this.datePipe
                            .transform(historicSiteData.timeStamp, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.historicSiteDataModalRef(component, historicSiteData);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.historicSiteDataModalRef(component, new HistoricSiteDataTpims());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    historicSiteDataModalRef(component: Component, historicSiteData: HistoricSiteDataTpims): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.historicSiteData = historicSiteData;
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
