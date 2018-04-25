import { BaseEntity } from './../../shared';

export class LogosTpims implements BaseEntity {
    constructor(
        public id?: number,
        public logoUrl?: string,
        public siteId?: number,
    ) {
    }
}
