import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SiteStatusTpimsComponent } from './site-status-tpims.component';
import { SiteStatusTpimsDetailComponent } from './site-status-tpims-detail.component';
import { SiteStatusTpimsPopupComponent } from './site-status-tpims-dialog.component';
import { SiteStatusTpimsDeletePopupComponent } from './site-status-tpims-delete-dialog.component';

export const siteStatusRoute: Routes = [
    {
        path: 'site-status-tpims',
        component: SiteStatusTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiteStatuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'site-status-tpims/:id',
        component: SiteStatusTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiteStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const siteStatusPopupRoute: Routes = [
    {
        path: 'site-status-tpims-new',
        component: SiteStatusTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiteStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'site-status-tpims/:id/edit',
        component: SiteStatusTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiteStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'site-status-tpims/:id/delete',
        component: SiteStatusTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiteStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
