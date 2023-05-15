import { HttpClient, HttpHeaders, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UtilitiesService } from "./utilities.service";
import { BaseService } from "./base.service";
import { environment } from "@environments/environment";
import { catchError } from "rxjs/internal/operators/catchError";

import { map } from "rxjs/operators";

import { Pagination } from "../models";

@Injectable({
    providedIn: "root",
})
export class GroupService extends BaseService {
    private _sharedHeaders = new HttpHeaders();
    constructor(
        private http: HttpClient,
        private utilitiesService: UtilitiesService
    ) {
        super();
        this._sharedHeaders = this._sharedHeaders.set(
            "Content-Type",
            "application/json"
        );
    }

    add(entity: any) {
        return this.http
            .post(`${environment.apiUrl}/groups/save`, JSON.stringify(entity), {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    update(id: string, entity: any) {
        return this.http
            .put(
                `${environment.apiUrl}/groups/put/${id}`,
                JSON.stringify(entity),
                { headers: this._sharedHeaders }
            )
            .pipe(catchError(this.handleError));
    }

    getDetail(id) {
        return this.http
            .get<any>(`${environment.apiUrl}/groups/get/${id}`, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    getAllPaging(filter, subjectId, pageIndex, pageSize) {
        let condition: string = "";
        if (filter != "" && filter != null) {
            condition += `(name~'*${filter}*' OR code~'*${filter}*' )`;
        }
        if (subjectId != null && subjectId != "") {
            condition += `subjectId:${subjectId}`;
        }
        if (condition != "") {
            return this.http
                .get<Pagination<any>>(
                    `${environment.apiUrl}/groups/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=${condition}`,
                    {
                        headers: this._sharedHeaders,
                    }
                )
                .pipe(
                    map((response: Pagination<any>) => {
                        return response;
                    }),
                    catchError(this.handleError)
                );
        }
        return this.http
            .get<Pagination<any>>(
                `${environment.apiUrl}/groups/${environment.getPage}?page=${pageIndex}&size=${pageSize}`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<any>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    getAllSubjectStudent(studentId) {
        return this.http
            .get<Pagination<any>>(
                `${environment.apiStudentUrl}/subject-student/${environment.getPage}?filter=students.id:${studentId}`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<any>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    getGiangVien() {
        return this.http
            .get<Pagination<any>>(
                `${environment.apiUrl}/tbl-user/${environment.getPage}?filter=role.id:3`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<any>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    delete(id) {
        return this.http
            .delete(environment.apiUrl + "/groups/del/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    importExcell(formData: FormData, groups_id: any) {
        return this.http
            .post(
                `${environment.apiUrl}/groups-student/import-sv-to-groups/${groups_id}`,
                formData,
                {
                    reportProgress: true,
                    observe: "events",
                }
            )
            .pipe(catchError(this.handleError));
    }

    getAllStudentIngroups(filter, pageIndex, pageSize) {
        return this.http
            .get<Pagination<any>>(
                `${environment.apiUrl}/groups-student/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=groups.id:${filter}`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<any>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }
}
