import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AmenitiesTpims } from './amenities-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AmenitiesTpims>;

@Injectable()
export class AmenitiesTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/amenities';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/amenities';

    constructor(private http: HttpClient) { }

    create(amenities: AmenitiesTpims): Observable<EntityResponseType> {
        const copy = this.convert(amenities);
        return this.http.post<AmenitiesTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(amenities: AmenitiesTpims): Observable<EntityResponseType> {
        const copy = this.convert(amenities);
        return this.http.put<AmenitiesTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AmenitiesTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AmenitiesTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<AmenitiesTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AmenitiesTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<AmenitiesTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<AmenitiesTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AmenitiesTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AmenitiesTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AmenitiesTpims[]>): HttpResponse<AmenitiesTpims[]> {
        const jsonResponse: AmenitiesTpims[] = res.body;
        const body: AmenitiesTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AmenitiesTpims.
     */
    private convertItemFromServer(amenities: AmenitiesTpims): AmenitiesTpims {
        const copy: AmenitiesTpims = Object.assign({}, amenities);
        return copy;
    }

    /**
     * Convert a AmenitiesTpims to a JSON which can be sent to the server.
     */
    private convert(amenities: AmenitiesTpims): AmenitiesTpims {
        const copy: AmenitiesTpims = Object.assign({}, amenities);
        return copy;
    }
}
