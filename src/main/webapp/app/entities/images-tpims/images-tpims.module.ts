import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    ImagesTpimsService,
    ImagesTpimsPopupService,
    ImagesTpimsComponent,
    ImagesTpimsDetailComponent,
    ImagesTpimsDialogComponent,
    ImagesTpimsPopupComponent,
    ImagesTpimsDeletePopupComponent,
    ImagesTpimsDeleteDialogComponent,
    imagesRoute,
    imagesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...imagesRoute,
    ...imagesPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ImagesTpimsComponent,
        ImagesTpimsDetailComponent,
        ImagesTpimsDialogComponent,
        ImagesTpimsDeleteDialogComponent,
        ImagesTpimsPopupComponent,
        ImagesTpimsDeletePopupComponent,
    ],
    entryComponents: [
        ImagesTpimsComponent,
        ImagesTpimsDialogComponent,
        ImagesTpimsPopupComponent,
        ImagesTpimsDeleteDialogComponent,
        ImagesTpimsDeletePopupComponent,
    ],
    providers: [
        ImagesTpimsService,
        ImagesTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsImagesTpimsModule {}
