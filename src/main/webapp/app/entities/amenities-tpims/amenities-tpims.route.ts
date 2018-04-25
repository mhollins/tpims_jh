import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AmenitiesTpimsComponent } from './amenities-tpims.component';
import { AmenitiesTpimsDetailComponent } from './amenities-tpims-detail.component';
import { AmenitiesTpimsPopupComponent } from './amenities-tpims-dialog.component';
import { AmenitiesTpimsDeletePopupComponent } from './amenities-tpims-delete-dialog.component';

export const amenitiesRoute: Routes = [
    {
        path: 'amenities-tpims',
        component: AmenitiesTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amenities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'amenities-tpims/:id',
        component: AmenitiesTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amenities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amenitiesPopupRoute: Routes = [
    {
        path: 'amenities-tpims-new',
        component: AmenitiesTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amenities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amenities-tpims/:id/edit',
        component: AmenitiesTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amenities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amenities-tpims/:id/delete',
        component: AmenitiesTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amenities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
