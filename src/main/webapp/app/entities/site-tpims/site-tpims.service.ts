import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SiteTpims } from './site-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SiteTpims>;

@Injectable()
export class SiteTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/sites';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/sites';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(site: SiteTpims): Observable<EntityResponseType> {
        const copy = this.convert(site);
        return this.http.post<SiteTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(site: SiteTpims): Observable<EntityResponseType> {
        const copy = this.convert(site);
        return this.http.put<SiteTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SiteTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SiteTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<SiteTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SiteTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<SiteTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<SiteTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SiteTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SiteTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SiteTpims[]>): HttpResponse<SiteTpims[]> {
        const jsonResponse: SiteTpims[] = res.body;
        const body: SiteTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SiteTpims.
     */
    private convertItemFromServer(site: SiteTpims): SiteTpims {
        const copy: SiteTpims = Object.assign({}, site);
        copy.staticDataUpdated = this.dateUtils
            .convertDateTimeFromServer(site.staticDataUpdated);
        return copy;
    }

    /**
     * Convert a SiteTpims to a JSON which can be sent to the server.
     */
    private convert(site: SiteTpims): SiteTpims {
        const copy: SiteTpims = Object.assign({}, site);

        copy.staticDataUpdated = this.dateUtils.toDate(site.staticDataUpdated);
        return copy;
    }
}
