import { BaseEntity } from './../../shared';

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
        public siteId?: number,
    ) {
    }
}
