import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LogosTpimsComponent } from './logos-tpims.component';
import { LogosTpimsDetailComponent } from './logos-tpims-detail.component';
import { LogosTpimsPopupComponent } from './logos-tpims-dialog.component';
import { LogosTpimsDeletePopupComponent } from './logos-tpims-delete-dialog.component';

export const logosRoute: Routes = [
    {
        path: 'logos-tpims',
        component: LogosTpimsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'logos-tpims/:id',
        component: LogosTpimsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logosPopupRoute: Routes = [
    {
        path: 'logos-tpims-new',
        component: LogosTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'logos-tpims/:id/edit',
        component: LogosTpimsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'logos-tpims/:id/delete',
        component: LogosTpimsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
