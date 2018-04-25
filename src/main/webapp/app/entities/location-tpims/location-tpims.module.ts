import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    LocationTpimsService,
    LocationTpimsPopupService,
    LocationTpimsComponent,
    LocationTpimsDetailComponent,
    LocationTpimsDialogComponent,
    LocationTpimsPopupComponent,
    LocationTpimsDeletePopupComponent,
    LocationTpimsDeleteDialogComponent,
    locationRoute,
    locationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...locationRoute,
    ...locationPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LocationTpimsComponent,
        LocationTpimsDetailComponent,
        LocationTpimsDialogComponent,
        LocationTpimsDeleteDialogComponent,
        LocationTpimsPopupComponent,
        LocationTpimsDeletePopupComponent,
    ],
    entryComponents: [
        LocationTpimsComponent,
        LocationTpimsDialogComponent,
        LocationTpimsPopupComponent,
        LocationTpimsDeleteDialogComponent,
        LocationTpimsDeletePopupComponent,
    ],
    providers: [
        LocationTpimsService,
        LocationTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsLocationTpimsModule {}
