import { Injectable } from "@angular/core";
import { BaseService } from "../base.service";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { UtilitiesService } from "../utilities.service";
import { environment } from "@environments/environment";
import { catchError, map } from "rxjs/operators";
import { Pagination } from "@app/shared/models";

@Injectable({
    providedIn: "root",
})
export class StudentService extends BaseService {
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

    login(userName: string, password: string) {
        let objectLogin = {
            username: userName,
            password: password,
        };
        return this.http
            .post(`${environment.apiUrl}/student/login`, objectLogin)
            .pipe(catchError(this.handleError));
    }

    add(entity: any) {
        return this.http
            .post(
                `${environment.apiUrl}/student/save`,
                JSON.stringify(entity),
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(catchError(this.handleError));
    }

    // update(id: string, entity: any) {
    //     return this.http
    //         .put(
    //             `${environment.apiUrl}/student/put/${id}`,
    //             JSON.stringify(entity),
    //             { headers: this._sharedHeaders }
    //         )
    //         .pipe(catchError(this.handleError));
    // }

    update(id: string, formData: FormData) {
        return this.http
            .put(`${environment.apiUrl}/students/update/${id}`, formData, {
                reportProgress: true,
                observe: "events",
            })
            .pipe(
                map((res: any) => {
                    console.log(res);
                    return res;
                }),
                catchError(this.handleError)
            );
    }

    getDetail(id) {
        return this.http
            .get<any>(`${environment.apiUrl}/students/get/${id}`, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    getAllPaging(filter, pageIndex, pageSize) {
        let condition: string = "";
        if (filter != "" && filter != null) {
            condition += `(fullName~'*${filter}*' OR studentCode~'*${filter}*' )`;
        }
        if (condition != "") {
            return this.http
                .get<Pagination<any>>(
                    `${environment.apiUrl}/students/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=${condition}`,
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
                `${environment.apiUrl}/students/${environment.getPage}?page=${pageIndex}&size=${pageSize}`,
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

    getAllPagingByUser() {
        return this.http
            .get<Pagination<any>>(
                `${environment.apiUrl}/student/${environment.getPage}`,
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
            .delete(environment.apiUrl + "/students/del/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    khoaTaiKhoan(id) {
        return this.http
            .delete(environment.apiUrl + "/students/khoa-tai-khoan/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    moTaiKhoan(id) {
        return this.http
            .delete(environment.apiUrl + "/students/mo-tai-khoan/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }
}
