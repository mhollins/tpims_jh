import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AmenitiesTpims } from './amenities-tpims.model';
import { AmenitiesTpimsService } from './amenities-tpims.service';

@Injectable()
export class AmenitiesTpimsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private amenitiesService: AmenitiesTpimsService

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
                this.amenitiesService.find(id)
                    .subscribe((amenitiesResponse: HttpResponse<AmenitiesTpims>) => {
                        const amenities: AmenitiesTpims = amenitiesResponse.body;
                        this.ngbModalRef = this.amenitiesModalRef(component, amenities);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.amenitiesModalRef(component, new AmenitiesTpims());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    amenitiesModalRef(component: Component, amenities: AmenitiesTpims): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.amenities = amenities;
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
