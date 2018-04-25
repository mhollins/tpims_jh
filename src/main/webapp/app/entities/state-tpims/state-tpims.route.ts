import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { StateTpimsComponent } from './state-tpims.component';
import { StateTpimsDetailComponent } from './state-tpims-detail.component';
import { StateTpimsPopupComponent } from './state-tpims-dialog.component';
import { StateTpimsDeletePopupComponent } from './state-tpims-delete-dialog.component';

export const stateRoute: Routes = [
    {
        path: 'state-tpims',
        component: StateTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'state-tpims/:id',
        component: StateTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statePopupRoute: Routes = [
    {
        path: 'state-tpims-new',
        component: StateTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'state-tpims/:id/edit',
        component: StateTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'state-tpims/:id/delete',
        component: StateTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
