import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { LocationTpims } from './location-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LocationTpims>;

@Injectable()
export class LocationTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/locations';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/locations';

    constructor(private http: HttpClient) { }

    create(location: LocationTpims): Observable<EntityResponseType> {
        const copy = this.convert(location);
        return this.http.post<LocationTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(location: LocationTpims): Observable<EntityResponseType> {
        const copy = this.convert(location);
        return this.http.put<LocationTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LocationTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LocationTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<LocationTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LocationTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<LocationTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<LocationTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LocationTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LocationTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LocationTpims[]>): HttpResponse<LocationTpims[]> {
        const jsonResponse: LocationTpims[] = res.body;
        const body: LocationTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LocationTpims.
     */
    private convertItemFromServer(location: LocationTpims): LocationTpims {
        const copy: LocationTpims = Object.assign({}, location);
        return copy;
    }

    /**
     * Convert a LocationTpims to a JSON which can be sent to the server.
     */
    private convert(location: LocationTpims): LocationTpims {
        const copy: LocationTpims = Object.assign({}, location);
        return copy;
    }
}
