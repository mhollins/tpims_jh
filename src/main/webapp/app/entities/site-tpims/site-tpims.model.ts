import { BaseEntity } from './../../shared';

export const enum OwnerShipOptions {
    'PR',
    'PU'
}

export class SiteTpims implements BaseEntity {
    constructor(
        public id?: number,
        public siteId?: string,
        public siteName?: string,
        public totalCapacity?: number,
        public lowThreshold?: number,
        public staticDataUpdated?: any,
        public ownership?: OwnerShipOptions,
        public locationId?: number,
        public devices?: BaseEntity[],
        public amenities?: BaseEntity[],
        public images?: BaseEntity[],
        public logos?: BaseEntity[],
        public statusId?: number,
    ) {
    }
}
