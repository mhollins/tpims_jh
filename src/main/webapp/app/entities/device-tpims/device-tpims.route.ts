import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DeviceTpimsComponent } from './device-tpims.component';
import { DeviceTpimsDetailComponent } from './device-tpims-detail.component';
import { DeviceTpimsPopupComponent } from './device-tpims-dialog.component';
import { DeviceTpimsDeletePopupComponent } from './device-tpims-delete-dialog.component';

export const deviceRoute: Routes = [
    {
        path: 'device-tpims',
        component: DeviceTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devices'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'device-tpims/:id',
        component: DeviceTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const devicePopupRoute: Routes = [
    {
        path: 'device-tpims-new',
        component: DeviceTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'device-tpims/:id/edit',
        component: DeviceTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'device-tpims/:id/delete',
        component: DeviceTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
