import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { LogosTpims } from './logos-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LogosTpims>;

@Injectable()
export class LogosTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/logos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/logos';

    constructor(private http: HttpClient) { }

    create(logos: LogosTpims): Observable<EntityResponseType> {
        const copy = this.convert(logos);
        return this.http.post<LogosTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(logos: LogosTpims): Observable<EntityResponseType> {
        const copy = this.convert(logos);
        return this.http.put<LogosTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LogosTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LogosTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<LogosTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LogosTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<LogosTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<LogosTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LogosTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LogosTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LogosTpims[]>): HttpResponse<LogosTpims[]> {
        const jsonResponse: LogosTpims[] = res.body;
        const body: LogosTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LogosTpims.
     */
    private convertItemFromServer(logos: LogosTpims): LogosTpims {
        const copy: LogosTpims = Object.assign({}, logos);
        return copy;
    }

    /**
     * Convert a LogosTpims to a JSON which can be sent to the server.
     */
    private convert(logos: LogosTpims): LogosTpims {
        const copy: LogosTpims = Object.assign({}, logos);
        return copy;
    }
}
