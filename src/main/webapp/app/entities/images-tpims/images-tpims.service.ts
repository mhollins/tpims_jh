import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ImagesTpims } from './images-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ImagesTpims>;

@Injectable()
export class ImagesTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/images';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/images';

    constructor(private http: HttpClient) { }

    create(images: ImagesTpims): Observable<EntityResponseType> {
        const copy = this.convert(images);
        return this.http.post<ImagesTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(images: ImagesTpims): Observable<EntityResponseType> {
        const copy = this.convert(images);
        return this.http.put<ImagesTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ImagesTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ImagesTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<ImagesTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ImagesTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ImagesTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<ImagesTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ImagesTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ImagesTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ImagesTpims[]>): HttpResponse<ImagesTpims[]> {
        const jsonResponse: ImagesTpims[] = res.body;
        const body: ImagesTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ImagesTpims.
     */
    private convertItemFromServer(images: ImagesTpims): ImagesTpims {
        const copy: ImagesTpims = Object.assign({}, images);
        return copy;
    }

    /**
     * Convert a ImagesTpims to a JSON which can be sent to the server.
     */
    private convert(images: ImagesTpims): ImagesTpims {
        const copy: ImagesTpims = Object.assign({}, images);
        return copy;
    }
}
