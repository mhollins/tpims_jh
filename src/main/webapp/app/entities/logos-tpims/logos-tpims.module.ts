import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    LogosTpimsService,
    LogosTpimsPopupService,
    LogosTpimsComponent,
    LogosTpimsDetailComponent,
    LogosTpimsDialogComponent,
    LogosTpimsPopupComponent,
    LogosTpimsDeletePopupComponent,
    LogosTpimsDeleteDialogComponent,
    logosRoute,
    logosPopupRoute,
} from './';

const ENTITY_STATES = [
    ...logosRoute,
    ...logosPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LogosTpimsComponent,
        LogosTpimsDetailComponent,
        LogosTpimsDialogComponent,
        LogosTpimsDeleteDialogComponent,
        LogosTpimsPopupComponent,
        LogosTpimsDeletePopupComponent,
    ],
    entryComponents: [
        LogosTpimsComponent,
        LogosTpimsDialogComponent,
        LogosTpimsPopupComponent,
        LogosTpimsDeleteDialogComponent,
        LogosTpimsDeletePopupComponent,
    ],
    providers: [
        LogosTpimsService,
        LogosTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsLogosTpimsModule {}
