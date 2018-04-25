import { BaseEntity } from './../../shared';

export class DeviceDataTpims implements BaseEntity {
    constructor(
        public id?: number,
        public timeStamp?: any,
        public deviceName?: string,
        public zone?: number,
        public status?: string,
        public speed?: number,
        public volume?: number,
        public occupancy?: number,
        public deviceId?: number,
    ) {
    }
}
