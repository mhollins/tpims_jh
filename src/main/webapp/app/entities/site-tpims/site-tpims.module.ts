import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    SiteTpimsService,
    SiteTpimsPopupService,
    SiteTpimsComponent,
    SiteTpimsDetailComponent,
    SiteTpimsDialogComponent,
    SiteTpimsPopupComponent,
    SiteTpimsDeletePopupComponent,
    SiteTpimsDeleteDialogComponent,
    siteRoute,
    sitePopupRoute,
} from './';

const ENTITY_STATES = [
    ...siteRoute,
    ...sitePopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SiteTpimsComponent,
        SiteTpimsDetailComponent,
        SiteTpimsDialogComponent,
        SiteTpimsDeleteDialogComponent,
        SiteTpimsPopupComponent,
        SiteTpimsDeletePopupComponent,
    ],
    entryComponents: [
        SiteTpimsComponent,
        SiteTpimsDialogComponent,
        SiteTpimsPopupComponent,
        SiteTpimsDeleteDialogComponent,
        SiteTpimsDeletePopupComponent,
    ],
    providers: [
        SiteTpimsService,
        SiteTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsSiteTpimsModule {}
