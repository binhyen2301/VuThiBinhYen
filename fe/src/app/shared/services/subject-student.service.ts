import { HttpClient, HttpHeaders, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UtilitiesService } from "./utilities.service";
import { BaseService } from "./base.service";
import { environment } from "@environments/environment";
import { catchError } from "rxjs/internal/operators/catchError";

import { map } from "rxjs/operators";
import { Subject } from "../models/subject";
import { Pagination } from "../models";

@Injectable({
    providedIn: "root",
})
export class SubjectStudentService extends BaseService {
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

    add(entity: Subject) {
        return this.http
            .post(
                `${environment.apiUrl}/subject-student/save`,
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
                `${environment.apiUrl}/subject-student/put/${id}`,
                JSON.stringify(entity),
                { headers: this._sharedHeaders }
            )
            .pipe(catchError(this.handleError));
    }

    dangKiNhom(id: string, entity: any) {
        return this.http
            .put(
                `${environment.apiUrl}/subject-student/dang-ki-nhom/${id}`,
                JSON.stringify(entity),
                { headers: this._sharedHeaders }
            )
            .pipe(catchError(this.handleError));
    }

    chamDiem(id: string, entity: any) {
        return this.http
            .put(
                `${environment.apiUrl}/subject-student/cham-diem/${id}`,
                JSON.stringify(entity),
                { headers: this._sharedHeaders }
            )
            .pipe(catchError(this.handleError));
    }

    getDetail(id) {
        return this.http
            .get<Subject>(`${environment.apiUrl}/subject-student/get/${id}`, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    getAllPaging(filter, pageIndex, pageSize) {
        let condition: string = "";
        if (filter != "" && filter != null) {
            condition += `(name~'*${filter}*' OR code~'*${filter}*' )`;
        }
        if (condition != "") {
            return this.http
                .get<Pagination<Subject>>(
                    `${environment.apiUrl}/subject-student/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=${condition}`,
                    {
                        headers: this._sharedHeaders,
                    }
                )
                .pipe(
                    map((response: Pagination<Subject>) => {
                        return response;
                    }),
                    catchError(this.handleError)
                );
        }
        return this.http
            .get<Pagination<Subject>>(
                `${environment.apiUrl}/subject-student/${environment.getPage}?page=${pageIndex}&size=${pageSize}`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<Subject>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    getAllPagingByUser() {
        return this.http
            .get<Pagination<Subject>>(
                `${environment.apiUrl}/subject-student/${environment.getPage}`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<Subject>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    getGiangVien() {
        return this.http
            .get<Pagination<Subject>>(
                `${environment.apiUrl}/tbl-user/${environment.getPage}?filter=role.id:3`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<Subject>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    delete(id) {
        return this.http
            .delete(environment.apiUrl + "/subject-student/del/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    importExcell(formData: FormData, subject_id: any) {
        return this.http
            .post(
                `${environment.apiUrl}/subject-student/import-sv-to-subject/${subject_id}`,
                formData,
                {
                    reportProgress: true,
                    observe: "events",
                }
            )
            .pipe(catchError(this.handleError));
    }

    getAllStudentInSubject(filter, pageIndex, pageSize) {
        return this.http
            .get<Pagination<any>>(
                `${environment.apiUrl}/subject-student/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=subject.id:${filter}`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<Subject>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    nopBai(formData: FormData, id: any) {
        return this.http
            .put(
                `${environment.apiUrl}/subject-student/nop-bai/${id}`,
                formData,
                {
                    reportProgress: true,
                    observe: "events",
                }
            )
            .pipe(catchError(this.handleError));
    }

    ketThucHocPhanS(id: any) {
        return this.http
            .put(`${environment.apiUrl}/subject/ket-thuc-hoc-phan/${id}`, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }
}
