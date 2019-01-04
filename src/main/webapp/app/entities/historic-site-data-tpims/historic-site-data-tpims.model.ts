import { BaseEntity } from './../../shared';

export const enum TrendOptions {
    'EMPTYING',
    'STEADY',
    'FILLING'
}

export class HistoricSiteDataTpims implements BaseEntity {
    constructor(
        public id?: number,
        public totalCapacity?: number,
        public availability?: number,
        public trend?: TrendOptions,
        public open?: boolean,
        public trustData?: boolean,
        public timeStamp?: any,
        public verificationCheck?: boolean,
        public trueAvailable?: number,
        public operatorName?: string,
        public siteId?: number,
    ) {
        this.open = false;
        this.trustData = false;
        this.verificationCheck = false;
    }
}
