import { BaseEntity } from './../../shared';

export class ImagesTpims implements BaseEntity {
    constructor(
        public id?: number,
        public imageUrl?: string,
        public siteId?: number,
    ) {
    }
}
