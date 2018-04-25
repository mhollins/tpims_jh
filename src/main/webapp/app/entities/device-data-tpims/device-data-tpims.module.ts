import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    DeviceDataTpimsService,
    DeviceDataTpimsPopupService,
    DeviceDataTpimsComponent,
    DeviceDataTpimsDetailComponent,
    DeviceDataTpimsDialogComponent,
    DeviceDataTpimsPopupComponent,
    DeviceDataTpimsDeletePopupComponent,
    DeviceDataTpimsDeleteDialogComponent,
    deviceDataRoute,
    deviceDataPopupRoute,
} from './';

const ENTITY_STATES = [
    ...deviceDataRoute,
    ...deviceDataPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DeviceDataTpimsComponent,
        DeviceDataTpimsDetailComponent,
        DeviceDataTpimsDialogComponent,
        DeviceDataTpimsDeleteDialogComponent,
        DeviceDataTpimsPopupComponent,
        DeviceDataTpimsDeletePopupComponent,
    ],
    entryComponents: [
        DeviceDataTpimsComponent,
        DeviceDataTpimsDialogComponent,
        DeviceDataTpimsPopupComponent,
        DeviceDataTpimsDeleteDialogComponent,
        DeviceDataTpimsDeletePopupComponent,
    ],
    providers: [
        DeviceDataTpimsService,
        DeviceDataTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsDeviceDataTpimsModule {}
