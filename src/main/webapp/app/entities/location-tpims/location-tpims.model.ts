import { BaseEntity } from './../../shared';

export class LocationTpims implements BaseEntity {
    constructor(
        public id?: number,
        public locationType?: string,
        public locationOwner?: string,
        public relevantHighway?: string,
        public referencePost?: string,
        public exitId?: string,
        public directionOfTravel?: string,
        public streetAdr?: string,
        public city?: string,
        public postalCode?: string,
        public timeZone?: string,
        public latitude?: number,
        public longitude?: number,
        public countyId?: number,
    ) {
    }
}
