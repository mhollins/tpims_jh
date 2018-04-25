import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DistrictTpimsComponent } from './district-tpims.component';
import { DistrictTpimsDetailComponent } from './district-tpims-detail.component';
import { DistrictTpimsPopupComponent } from './district-tpims-dialog.component';
import { DistrictTpimsDeletePopupComponent } from './district-tpims-delete-dialog.component';

export const districtRoute: Routes = [
    {
        path: 'district-tpims',
        component: DistrictTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Districts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'district-tpims/:id',
        component: DistrictTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Districts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const districtPopupRoute: Routes = [
    {
        path: 'district-tpims-new',
        component: DistrictTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Districts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'district-tpims/:id/edit',
        component: DistrictTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Districts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'district-tpims/:id/delete',
        component: DistrictTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Districts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
