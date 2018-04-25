import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DeviceTpims } from './device-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DeviceTpims>;

@Injectable()
export class DeviceTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/devices';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/devices';

    constructor(private http: HttpClient) { }

    create(device: DeviceTpims): Observable<EntityResponseType> {
        const copy = this.convert(device);
        return this.http.post<DeviceTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(device: DeviceTpims): Observable<EntityResponseType> {
        const copy = this.convert(device);
        return this.http.put<DeviceTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DeviceTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DeviceTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<DeviceTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DeviceTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DeviceTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<DeviceTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DeviceTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DeviceTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DeviceTpims[]>): HttpResponse<DeviceTpims[]> {
        const jsonResponse: DeviceTpims[] = res.body;
        const body: DeviceTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DeviceTpims.
     */
    private convertItemFromServer(device: DeviceTpims): DeviceTpims {
        const copy: DeviceTpims = Object.assign({}, device);
        return copy;
    }

    /**
     * Convert a DeviceTpims to a JSON which can be sent to the server.
     */
    private convert(device: DeviceTpims): DeviceTpims {
        const copy: DeviceTpims = Object.assign({}, device);
        return copy;
    }
}
