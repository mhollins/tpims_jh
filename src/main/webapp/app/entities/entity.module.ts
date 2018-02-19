import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TpimsStateTpimsModule } from './state-tpims/state-tpims.module';
import { TpimsDistrictTpimsModule } from './district-tpims/district-tpims.module';
import { TpimsCountyTpimsModule } from './county-tpims/county-tpims.module';
import { TpimsLocationTpimsModule } from './location-tpims/location-tpims.module';
import { TpimsSiteTpimsModule } from './site-tpims/site-tpims.module';
import { TpimsAmenitiesTpimsModule } from './amenities-tpims/amenities-tpims.module';
import { TpimsImagesTpimsModule } from './images-tpims/images-tpims.module';
import { TpimsLogosTpimsModule } from './logos-tpims/logos-tpims.module';
import { TpimsSiteStatusTpimsModule } from './site-status-tpims/site-status-tpims.module';
import { TpimsHistoricSiteDataTpimsModule } from './historic-site-data-tpims/historic-site-data-tpims.module';
import { TpimsDeviceTpimsModule } from './device-tpims/device-tpims.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TpimsStateTpimsModule,
        TpimsDistrictTpimsModule,
        TpimsCountyTpimsModule,
        TpimsLocationTpimsModule,
        TpimsSiteTpimsModule,
        TpimsAmenitiesTpimsModule,
        TpimsImagesTpimsModule,
        TpimsLogosTpimsModule,
        TpimsSiteStatusTpimsModule,
        TpimsHistoricSiteDataTpimsModule,
        TpimsDeviceTpimsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TpimsEntityModule {}
