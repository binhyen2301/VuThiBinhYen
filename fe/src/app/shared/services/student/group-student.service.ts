import { HttpClient, HttpHeaders, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { environment } from "@environments/environment";
import { catchError } from "rxjs/internal/operators/catchError";

import { map } from "rxjs/operators";
import { UtilitiesService } from "../utilities.service";
import { BaseService } from "../base.service";
import { Pagination } from "@app/shared/models";
import { Subject } from "rxjs";
import { AuthService } from "../auth.service";

@Injectable({
    providedIn: "root",
})
export class GroupStudentService extends BaseService {
    private _sharedHeaders = new HttpHeaders();
    constructor(
        private http: HttpClient,
        private utilitiesService: UtilitiesService,
        private authenService: AuthService
    ) {
        super();
        this._sharedHeaders = this._sharedHeaders.set(
            "Content-Type",
            "application/json"
        );
    }

    add(entity: any) {
        return this.http
            .post(
                `${environment.apiStudentUrl}/groups/save`,
                JSON.stringify(entity),
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(catchError(this.handleError));
    }

    update(id: string, entity: any) {
        return this.http
            .put(
                `${environment.apiStudentUrl}/groups/put/${id}`,
                JSON.stringify(entity),
                { headers: this._sharedHeaders }
            )
            .pipe(catchError(this.handleError));
    }

    getDetail(id) {
        return this.http
            .get<any>(`${environment.apiStudentUrl}/groups/get/${id}`, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    getAllPaging(maSv, hoTen, classId, groupId, pageIndex, pageSize) {
        let studentId = this.authenService.user.id;
        let condition: string = "";

        if (classId != null && classId != "") {
            condition += `AND students.classId:${classId}`;
        }
        if (groupId != null && groupId != "") {
            condition += `AND group.id:${groupId}`;
        }

        if (condition != "") {
            return this.http
                .get<Pagination<any>>(
                    `${environment.apiStudentUrl}/subject-student/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=students.id:${studentId} AND ${condition}`,
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
                `${environment.apiStudentUrl}/subject-student/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=students.id:${studentId}`,
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
                `${environment.apiStudentUrl}/tbl-user/${environment.getPage}?filter=role.id:3`,
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
            .delete(environment.apiStudentUrl + "/groups/del/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    importExcell(formData: FormData, groups_id: any) {
        return this.http
            .post(
                `${environment.apiStudentUrl}/groups-student/import-sv-to-groups/${groups_id}`,
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
                `${environment.apiStudentUrl}/groups-student/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=groups.id:${filter}`,
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
