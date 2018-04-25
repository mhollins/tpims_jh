import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DeviceDataTpims } from './device-data-tpims.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DeviceDataTpims>;

@Injectable()
export class DeviceDataTpimsService {

    private resourceUrl =  SERVER_API_URL + 'api/device-data';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/device-data';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(deviceData: DeviceDataTpims): Observable<EntityResponseType> {
        const copy = this.convert(deviceData);
        return this.http.post<DeviceDataTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(deviceData: DeviceDataTpims): Observable<EntityResponseType> {
        const copy = this.convert(deviceData);
        return this.http.put<DeviceDataTpims>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DeviceDataTpims>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DeviceDataTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<DeviceDataTpims[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DeviceDataTpims[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DeviceDataTpims[]>> {
        const options = createRequestOption(req);
        return this.http.get<DeviceDataTpims[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DeviceDataTpims[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DeviceDataTpims = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DeviceDataTpims[]>): HttpResponse<DeviceDataTpims[]> {
        const jsonResponse: DeviceDataTpims[] = res.body;
        const body: DeviceDataTpims[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DeviceDataTpims.
     */
    private convertItemFromServer(deviceData: DeviceDataTpims): DeviceDataTpims {
        const copy: DeviceDataTpims = Object.assign({}, deviceData);
        copy.timeStamp = this.dateUtils
            .convertDateTimeFromServer(deviceData.timeStamp);
        return copy;
    }

    /**
     * Convert a DeviceDataTpims to a JSON which can be sent to the server.
     */
    private convert(deviceData: DeviceDataTpims): DeviceDataTpims {
        const copy: DeviceDataTpims = Object.assign({}, deviceData);

        copy.timeStamp = this.dateUtils.toDate(deviceData.timeStamp);
        return copy;
    }
}
