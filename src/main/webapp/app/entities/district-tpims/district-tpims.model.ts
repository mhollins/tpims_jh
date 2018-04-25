import { BaseEntity } from './../../shared';

export class DistrictTpims implements BaseEntity {
    constructor(
        public id?: number,
        public districtName?: string,
        public stateId?: number,
    ) {
    }
}
