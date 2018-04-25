import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SiteTpimsComponent } from './site-tpims.component';
import { SiteTpimsDetailComponent } from './site-tpims-detail.component';
import { SiteTpimsPopupComponent } from './site-tpims-dialog.component';
import { SiteTpimsDeletePopupComponent } from './site-tpims-delete-dialog.component';

export const siteRoute: Routes = [
    {
        path: 'site-tpims',
        component: SiteTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sites'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'site-tpims/:id',
        component: SiteTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sites'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sitePopupRoute: Routes = [
    {
        path: 'site-tpims-new',
        component: SiteTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'site-tpims/:id/edit',
        component: SiteTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'site-tpims/:id/delete',
        component: SiteTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
