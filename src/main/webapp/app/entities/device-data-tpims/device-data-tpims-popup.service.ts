import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { DeviceDataTpims } from './device-data-tpims.model';
import { DeviceDataTpimsService } from './device-data-tpims.service';

@Injectable()
export class DeviceDataTpimsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private deviceDataService: DeviceDataTpimsService

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
                this.deviceDataService.find(id)
                    .subscribe((deviceDataResponse: HttpResponse<DeviceDataTpims>) => {
                        const deviceData: DeviceDataTpims = deviceDataResponse.body;
                        deviceData.timeStamp = this.datePipe
                            .transform(deviceData.timeStamp, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.deviceDataModalRef(component, deviceData);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.deviceDataModalRef(component, new DeviceDataTpims());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    deviceDataModalRef(component: Component, deviceData: DeviceDataTpims): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.deviceData = deviceData;
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
