import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    AmenitiesTpimsService,
    AmenitiesTpimsPopupService,
    AmenitiesTpimsComponent,
    AmenitiesTpimsDetailComponent,
    AmenitiesTpimsDialogComponent,
    AmenitiesTpimsPopupComponent,
    AmenitiesTpimsDeletePopupComponent,
    AmenitiesTpimsDeleteDialogComponent,
    amenitiesRoute,
    amenitiesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...amenitiesRoute,
    ...amenitiesPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AmenitiesTpimsComponent,
        AmenitiesTpimsDetailComponent,
        AmenitiesTpimsDialogComponent,
        AmenitiesTpimsDeleteDialogComponent,
        AmenitiesTpimsPopupComponent,
        AmenitiesTpimsDeletePopupComponent,
    ],
    entryComponents: [
        AmenitiesTpimsComponent,
        AmenitiesTpimsDialogComponent,
        AmenitiesTpimsPopupComponent,
        AmenitiesTpimsDeleteDialogComponent,
        AmenitiesTpimsDeletePopupComponent,
    ],
    providers: [
        AmenitiesTpimsService,
        AmenitiesTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsAmenitiesTpimsModule {}
