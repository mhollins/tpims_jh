import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CountyTpimsComponent } from './county-tpims.component';
import { CountyTpimsDetailComponent } from './county-tpims-detail.component';
import { CountyTpimsPopupComponent } from './county-tpims-dialog.component';
import { CountyTpimsDeletePopupComponent } from './county-tpims-delete-dialog.component';

export const countyRoute: Routes = [
    {
        path: 'county-tpims',
        component: CountyTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'county-tpims/:id',
        component: CountyTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countyPopupRoute: Routes = [
    {
        path: 'county-tpims-new',
        component: CountyTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'county-tpims/:id/edit',
        component: CountyTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'county-tpims/:id/delete',
        component: CountyTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
