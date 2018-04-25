import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CountyTpims } from './county-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CountyTpims>;

@Injectable()
export class CountyTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/counties';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/counties';

    constructor(private http: HttpClient) { }

    create(county: CountyTpims): Observable<EntityResponseType> {
        const copy = this.convert(county);
        return this.http.post<CountyTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(county: CountyTpims): Observable<EntityResponseType> {
        const copy = this.convert(county);
        return this.http.put<CountyTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CountyTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CountyTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<CountyTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CountyTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<CountyTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<CountyTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CountyTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CountyTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CountyTpims[]>): HttpResponse<CountyTpims[]> {
        const jsonResponse: CountyTpims[] = res.body;
        const body: CountyTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CountyTpims.
     */
    private convertItemFromServer(county: CountyTpims): CountyTpims {
        const copy: CountyTpims = Object.assign({}, county);
        return copy;
    }

    /**
     * Convert a CountyTpims to a JSON which can be sent to the server.
     */
    private convert(county: CountyTpims): CountyTpims {
        const copy: CountyTpims = Object.assign({}, county);
        return copy;
    }
}
