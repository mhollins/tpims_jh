import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    SiteStatusTpimsService,
    SiteStatusTpimsPopupService,
    SiteStatusTpimsComponent,
    SiteStatusTpimsDetailComponent,
    SiteStatusTpimsDialogComponent,
    SiteStatusTpimsPopupComponent,
    SiteStatusTpimsDeletePopupComponent,
    SiteStatusTpimsDeleteDialogComponent,
    siteStatusRoute,
    siteStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...siteStatusRoute,
    ...siteStatusPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SiteStatusTpimsComponent,
        SiteStatusTpimsDetailComponent,
        SiteStatusTpimsDialogComponent,
        SiteStatusTpimsDeleteDialogComponent,
        SiteStatusTpimsPopupComponent,
        SiteStatusTpimsDeletePopupComponent,
    ],
    entryComponents: [
        SiteStatusTpimsComponent,
        SiteStatusTpimsDialogComponent,
        SiteStatusTpimsPopupComponent,
        SiteStatusTpimsDeleteDialogComponent,
        SiteStatusTpimsDeletePopupComponent,
    ],
    providers: [
        SiteStatusTpimsService,
        SiteStatusTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsSiteStatusTpimsModule {}
