import { BaseEntity } from './../../shared';

export class CountyTpims implements BaseEntity {
    constructor(
        public id?: number,
        public countyName?: string,
        public districtId?: number,
    ) {
    }
}
