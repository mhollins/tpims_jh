import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { HistoricSiteDataTpims } from './historic-site-data-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HistoricSiteDataTpims>;

@Injectable()
export class HistoricSiteDataTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/historic-site-data';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/historic-site-data';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(historicSiteData: HistoricSiteDataTpims): Observable<EntityResponseType> {
        const copy = this.convert(historicSiteData);
        return this.http.post<HistoricSiteDataTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(historicSiteData: HistoricSiteDataTpims): Observable<EntityResponseType> {
        const copy = this.convert(historicSiteData);
        return this.http.put<HistoricSiteDataTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HistoricSiteDataTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HistoricSiteDataTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<HistoricSiteDataTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HistoricSiteDataTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HistoricSiteDataTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<HistoricSiteDataTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HistoricSiteDataTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HistoricSiteDataTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HistoricSiteDataTpims[]>): HttpResponse<HistoricSiteDataTpims[]> {
        const jsonResponse: HistoricSiteDataTpims[] = res.body;
        const body: HistoricSiteDataTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HistoricSiteDataTpims.
     */
    private convertItemFromServer(historicSiteData: HistoricSiteDataTpims): HistoricSiteDataTpims {
        const copy: HistoricSiteDataTpims = Object.assign({}, historicSiteData);
        copy.timeStamp = this.dateUtils
            .convertDateTimeFromServer(historicSiteData.timeStamp);
        return copy;
    }

    /**
     * Convert a HistoricSiteDataTpims to a JSON which can be sent to the server.
     */
    private convert(historicSiteData: HistoricSiteDataTpims): HistoricSiteDataTpims {
        const copy: HistoricSiteDataTpims = Object.assign({}, historicSiteData);

        copy.timeStamp = this.dateUtils.toDate(historicSiteData.timeStamp);
        return copy;
    }
}
