import { BaseEntity } from './../../shared';

export class AmenitiesTpims implements BaseEntity {
    constructor(
        public id?: number,
        public amenity?: string,
        public siteId?: number,
    ) {
    }
}
