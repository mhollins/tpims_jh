import { BaseEntity } from './../../shared';

export class StateTpims implements BaseEntity {
    constructor(
        public id?: number,
        public stateName?: string,
    ) {
    }
}
