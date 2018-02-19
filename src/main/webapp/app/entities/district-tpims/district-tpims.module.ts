import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    DistrictTpimsService,
    DistrictTpimsPopupService,
    DistrictTpimsComponent,
    DistrictTpimsDetailComponent,
    DistrictTpimsDialogComponent,
    DistrictTpimsPopupComponent,
    DistrictTpimsDeletePopupComponent,
    DistrictTpimsDeleteDialogComponent,
    districtRoute,
    districtPopupRoute,
} from './';

const ENTITY_STATES = [
    ...districtRoute,
    ...districtPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DistrictTpimsComponent,
        DistrictTpimsDetailComponent,
        DistrictTpimsDialogComponent,
        DistrictTpimsDeleteDialogComponent,
        DistrictTpimsPopupComponent,
        DistrictTpimsDeletePopupComponent,
    ],
    entryComponents: [
        DistrictTpimsComponent,
        DistrictTpimsDialogComponent,
        DistrictTpimsPopupComponent,
        DistrictTpimsDeleteDialogComponent,
        DistrictTpimsDeletePopupComponent,
    ],
    providers: [
        DistrictTpimsService,
        DistrictTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsDistrictTpimsModule {}
