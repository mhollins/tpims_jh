import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpimsSharedModule } from '../../shared';
import {
    HistoricSiteDataTpimsService,
    HistoricSiteDataTpimsPopupService,
    HistoricSiteDataTpimsComponent,
    HistoricSiteDataTpimsDetailComponent,
    HistoricSiteDataTpimsDialogComponent,
    HistoricSiteDataTpimsPopupComponent,
    HistoricSiteDataTpimsDeletePopupComponent,
    HistoricSiteDataTpimsDeleteDialogComponent,
    historicSiteDataRoute,
    historicSiteDataPopupRoute,
} from './';

const ENTITY_STATES = [
    ...historicSiteDataRoute,
    ...historicSiteDataPopupRoute,
];

@NgModule({
    imports: [
        TpimsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HistoricSiteDataTpimsComponent,
        HistoricSiteDataTpimsDetailComponent,
        HistoricSiteDataTpimsDialogComponent,
        HistoricSiteDataTpimsDeleteDialogComponent,
        HistoricSiteDataTpimsPopupComponent,
        HistoricSiteDataTpimsDeletePopupComponent,
    ],
    entryComponents: [
        HistoricSiteDataTpimsComponent,
        HistoricSiteDataTpimsDialogComponent,
        HistoricSiteDataTpimsPopupComponent,
        HistoricSiteDataTpimsDeleteDialogComponent,
        HistoricSiteDataTpimsDeletePopupComponent,
    ],
    providers: [
        HistoricSiteDataTpimsService,
        HistoricSiteDataTpimsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsHistoricSiteDataTpimsModule {}
