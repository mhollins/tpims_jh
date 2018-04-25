import { BaseEntity } from './../../shared';

export const enum LocationFunctions {
    'ENTRANCE',
    'EXIT'
}

export class DeviceTpims implements BaseEntity {
    constructor(
        public id?: number,
        public deviceName?: string,
        public ipAddress?: string,
        public ipPort?: number,
        public deviceAddress?: number,
        public pollingRate?: number,
        public jmsDomain?: string,
        public timeout?: number,
        public locationfunction?: LocationFunctions,
        public siteId?: number,
    ) {
    }
}
