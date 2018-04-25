import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HistoricSiteDataTpimsComponent } from './historic-site-data-tpims.component';
import { HistoricSiteDataTpimsDetailComponent } from './historic-site-data-tpims-detail.component';
import { HistoricSiteDataTpimsPopupComponent } from './historic-site-data-tpims-dialog.component';
import { HistoricSiteDataTpimsDeletePopupComponent } from './historic-site-data-tpims-delete-dialog.component';

export const historicSiteDataRoute: Routes = [
    {
        path: 'historic-site-data-tpims',
        component: HistoricSiteDataTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoricSiteData'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'historic-site-data-tpims/:id',
        component: HistoricSiteDataTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoricSiteData'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const historicSiteDataPopupRoute: Routes = [
    {
        path: 'historic-site-data-tpims-new',
        component: HistoricSiteDataTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoricSiteData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'historic-site-data-tpims/:id/edit',
        component: HistoricSiteDataTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoricSiteData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'historic-site-data-tpims/:id/delete',
        component: HistoricSiteDataTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoricSiteData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
