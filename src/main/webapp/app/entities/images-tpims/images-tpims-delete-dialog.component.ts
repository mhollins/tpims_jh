import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ImagesTpims } from './images-tpims.model';
import { ImagesTpimsPopupService } from './images-tpims-popup.service';
import { ImagesTpimsService } from './images-tpims.service';

@Component({
    selector: 'jhi-images-tpims-delete-dialog',
    templateUrl: './images-tpims-delete-dialog.component.html'
})
export class ImagesTpimsDeleteDialogComponent {

    images: ImagesTpims;

    constructor(
        private imagesService: ImagesTpimsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.imagesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'imagesListModification',
                content: 'Deleted an images'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-images-tpims-delete-popup',
    template: ''
})
export class ImagesTpimsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imagesPopupService: ImagesTpimsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.imagesPopupService
                .open(ImagesTpimsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
