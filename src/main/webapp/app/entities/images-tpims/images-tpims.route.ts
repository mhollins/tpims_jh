import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ImagesTpimsComponent } from './images-tpims.component';
import { ImagesTpimsDetailComponent } from './images-tpims-detail.component';
import { ImagesTpimsPopupComponent } from './images-tpims-dialog.component';
import { ImagesTpimsDeletePopupComponent } from './images-tpims-delete-dialog.component';

export const imagesRoute: Routes = [
    {
        path: 'images-tpims',
        component: ImagesTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'images-tpims/:id',
        component: ImagesTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imagesPopupRoute: Routes = [
    {
        path: 'images-tpims-new',
        component: ImagesTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'images-tpims/:id/edit',
        component: ImagesTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'images-tpims/:id/delete',
        component: ImagesTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
