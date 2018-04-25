import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LocationTpimsComponent } from './location-tpims.component';
import { LocationTpimsDetailComponent } from './location-tpims-detail.component';
import { LocationTpimsPopupComponent } from './location-tpims-dialog.component';
import { LocationTpimsDeletePopupComponent } from './location-tpims-delete-dialog.component';

export const locationRoute: Routes = [
    {
        path: 'location-tpims',
        component: LocationTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'location-tpims/:id',
        component: LocationTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const locationPopupRoute: Routes = [
    {
        path: 'location-tpims-new',
        component: LocationTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location-tpims/:id/edit',
        component: LocationTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location-tpims/:id/delete',
        component: LocationTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
