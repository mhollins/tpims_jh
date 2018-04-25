import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    StateTpimsService,
    StateTpimsPopupService,
    StateTpimsComponent,
    StateTpimsDetailComponent,
    StateTpimsDialogComponent,
    StateTpimsPopupComponent,
    StateTpimsDeletePopupComponent,
    StateTpimsDeleteDialogComponent,
    stateRoute,
    statePopupRoute,
} from './';

const ENTITY_STATES = [
    ...stateRoute,
    ...statePopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StateTpimsComponent,
        StateTpimsDetailComponent,
        StateTpimsDialogComponent,
        StateTpimsDeleteDialogComponent,
        StateTpimsPopupComponent,
        StateTpimsDeletePopupComponent,
    ],
    entryComponents: [
        StateTpimsComponent,
        StateTpimsDialogComponent,
        StateTpimsPopupComponent,
        StateTpimsDeleteDialogComponent,
        StateTpimsDeletePopupComponent,
    ],
    providers: [
        StateTpimsService,
        StateTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsStateTpimsModule {}
