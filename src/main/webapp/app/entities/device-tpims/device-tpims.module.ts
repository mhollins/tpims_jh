import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    DeviceTpimsService,
    DeviceTpimsPopupService,
    DeviceTpimsComponent,
    DeviceTpimsDetailComponent,
    DeviceTpimsDialogComponent,
    DeviceTpimsPopupComponent,
    DeviceTpimsDeletePopupComponent,
    DeviceTpimsDeleteDialogComponent,
    deviceRoute,
    devicePopupRoute,
} from './';

const ENTITY_STATES = [
    ...deviceRoute,
    ...devicePopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DeviceTpimsComponent,
        DeviceTpimsDetailComponent,
        DeviceTpimsDialogComponent,
        DeviceTpimsDeleteDialogComponent,
        DeviceTpimsPopupComponent,
        DeviceTpimsDeletePopupComponent,
    ],
    entryComponents: [
        DeviceTpimsComponent,
        DeviceTpimsDialogComponent,
        DeviceTpimsPopupComponent,
        DeviceTpimsDeleteDialogComponent,
        DeviceTpimsDeletePopupComponent,
    ],
    providers: [
        DeviceTpimsService,
        DeviceTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsDeviceTpimsModule {}
