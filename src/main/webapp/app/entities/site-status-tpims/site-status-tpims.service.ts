import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SiteStatusTpims } from './site-status-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SiteStatusTpims>;

@Injectable()
export class SiteStatusTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/site-statuses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/site-statuses';
    private updateUrl = SERVER_API_URL + 'api/operator-update';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(siteStatus: SiteStatusTpims): Observable<EntityResponseType> {
        const copy = this.convert(siteStatus);
        return this.http.post<SiteStatusTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(siteStatus: SiteStatusTpims): Observable<EntityResponseType> {
        const copy = this.convert(siteStatus);
        return this.http.put<SiteStatusTpims>(this.updateUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SiteStatusTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SiteStatusTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<SiteStatusTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SiteStatusTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<SiteStatusTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<SiteStatusTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SiteStatusTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SiteStatusTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SiteStatusTpims[]>): HttpResponse<SiteStatusTpims[]> {
        const jsonResponse: SiteStatusTpims[] = res.body;
        const body: SiteStatusTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SiteStatusTpims.
     */
    private convertItemFromServer(siteStatus: SiteStatusTpims): SiteStatusTpims {
        const copy: SiteStatusTpims = Object.assign({}, siteStatus);
        copy.lastDeviceUpdate = this.dateUtils
            .convertDateTimeFromServer(siteStatus.lastDeviceUpdate);
        copy.lastOperatorUpdate = this.dateUtils
            .convertDateTimeFromServer(siteStatus.lastOperatorUpdate);
        return copy;
    }

    /**
     * Convert a SiteStatusTpims to a JSON which can be sent to the server.
     */
    private convert(siteStatus: SiteStatusTpims): SiteStatusTpims {
        const copy: SiteStatusTpims = Object.assign({}, siteStatus);

        copy.lastDeviceUpdate = this.dateUtils.toDate(siteStatus.lastDeviceUpdate);

        copy.lastOperatorUpdate = this.dateUtils.toDate(siteStatus.lastOperatorUpdate);
        return copy;
    }
}
