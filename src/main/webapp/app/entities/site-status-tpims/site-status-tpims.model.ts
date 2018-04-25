import { BaseEntity } from './../../shared';

export const enum TrendOptions {
    'EMPTYING',
    'STEADY',
    'FILLING'
}

export class SiteStatusTpims implements BaseEntity {
    constructor(
        public id?: number,
        public reportedAvailable?: number,
        public trend?: TrendOptions,
        public open?: boolean,
        public trustData?: boolean,
        public lastDeviceUpdate?: any,
        public lastOperatorUpdate?: any,
        public siteId?: number,
    ) {
        this.open = false;
        this.trustData = false;
    }
}
