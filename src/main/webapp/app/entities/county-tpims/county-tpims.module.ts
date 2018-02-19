import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    CountyTpimsService,
    CountyTpimsPopupService,
    CountyTpimsComponent,
    CountyTpimsDetailComponent,
    CountyTpimsDialogComponent,
    CountyTpimsPopupComponent,
    CountyTpimsDeletePopupComponent,
    CountyTpimsDeleteDialogComponent,
    countyRoute,
    countyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...countyRoute,
    ...countyPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CountyTpimsComponent,
        CountyTpimsDetailComponent,
        CountyTpimsDialogComponent,
        CountyTpimsDeleteDialogComponent,
        CountyTpimsPopupComponent,
        CountyTpimsDeletePopupComponent,
    ],
    entryComponents: [
        CountyTpimsComponent,
        CountyTpimsDialogComponent,
        CountyTpimsPopupComponent,
        CountyTpimsDeleteDialogComponent,
        CountyTpimsDeletePopupComponent,
    ],
    providers: [
        CountyTpimsService,
        CountyTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsCountyTpimsModule {}
