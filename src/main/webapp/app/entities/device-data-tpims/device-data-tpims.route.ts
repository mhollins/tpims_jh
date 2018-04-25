import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DeviceDataTpimsComponent } from './device-data-tpims.component';
import { DeviceDataTpimsDetailComponent } from './device-data-tpims-detail.component';
import { DeviceDataTpimsPopupComponent } from './device-data-tpims-dialog.component';
import { DeviceDataTpimsDeletePopupComponent } from './device-data-tpims-delete-dialog.component';

export const deviceDataRoute: Routes = [
    {
        path: 'device-data-tpims',
        component: DeviceDataTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeviceData'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'device-data-tpims/:id',
        component: DeviceDataTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeviceData'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deviceDataPopupRoute: Routes = [
    {
        path: 'device-data-tpims-new',
        component: DeviceDataTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeviceData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'device-data-tpims/:id/edit',
        component: DeviceDataTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeviceData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'device-data-tpims/:id/delete',
        component: DeviceDataTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeviceData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
