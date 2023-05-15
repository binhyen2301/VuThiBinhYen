import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { BaseService } from "./base.service";
import { catchError, map } from "rxjs/operators";
import { environment } from "@environments/environment";
import { User, Pagination } from "../models";
import { UtilitiesService } from "./utilities.service";

@Injectable({ providedIn: "root" })
export class UsersService extends BaseService {
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
    add(entity: User) {
        return this.http
            .post(
                `${environment.apiUrl}/tbl-user/save`,
                JSON.stringify(entity),
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(catchError(this.handleError));
    }

    update(id: string, entity: User) {
        return this.http
            .put(
                `${environment.apiUrl}/tbl-user/put/${id}`,
                JSON.stringify(entity),
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(catchError(this.handleError));
    }

    getDetail(id) {
        return this.http
            .get<User>(`${environment.apiUrl}/tbl-user/get/${id}`, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    getAllPaging(filter, pageIndex, pageSize) {
        let condition: string = "";
        if (filter != "" && filter != null) {
            condition += `(fullName~'*${filter}*' OR username~'*${filter}*')`;
        }
        if (condition != "") {
            return this.http
                .get<Pagination<any>>(
                    `${environment.apiUrl}/tbl-user/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=${condition}`,
                    {
                        headers: this._sharedHeaders,
                    }
                )
                .pipe(
                    map((response: Pagination<User>) => {
                        return response;
                    }),
                    catchError(this.handleError)
                );
        }
        return this.http
            .get<Pagination<any>>(
                `${environment.apiUrl}/tbl-user/${environment.getPage}?page=${pageIndex}&size=${pageSize}`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<User>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    delete(id) {
        return this.http
            .delete(environment.apiUrl + "/tbl-user/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    khoaTaiKhoan(id) {
        return this.http
            .delete(environment.apiUrl + "/tbl-user/khoa-tai-khoan/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    moTaiKhoan(id) {
        return this.http
            .delete(environment.apiUrl + "/tbl-user/mo-tai-khoan/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    getMenuByUser(userId: string) {
        return this.http
            .get<Function[]>(`${environment.apiUrl}/tbl-user/${userId}/menu`, {
                headers: this._sharedHeaders,
            })
            .pipe(
                map((response) => {
                    const functions =
                        this.utilitiesService.UnflatteringForLeftMenu(response);
                    return functions;
                }),
                catchError(this.handleError)
            );
    }

    getUserRoles(userId: string) {
        return this.http
            .get<string[]>(
                `${environment.apiUrl}/api/tbl-user/${userId}/roles`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(catchError(this.handleError));
    }

    removeRolesFromUser(id, roleNames: string[]) {
        let rolesQuery = "";
        for (const roleName of roleNames) {
            rolesQuery += "roleNames" + "=" + roleName + "&";
        }
        return this.http
            .delete(
                environment.apiUrl +
                    "/api/users/" +
                    id +
                    "/roles?" +
                    rolesQuery,
                { headers: this._sharedHeaders }
            )
            .pipe(catchError(this.handleError));
    }

    assignRolesToUser(userId: string, assignRolesToUser: any) {
        return this.http
            .post(
                `${environment.apiUrl}/api/tbl-user/${userId}/roles`,
                JSON.stringify(assignRolesToUser),
                { headers: this._sharedHeaders }
            )
            .pipe(catchError(this.handleError));
    }

    login(userName: string, password: string) {
        let objectLogin = {
            username: userName,
            password: password,
        };
        return this.http
            .post(`${environment.apiUrl}/login`, objectLogin)
            .pipe(catchError(this.handleError));
    }
}
