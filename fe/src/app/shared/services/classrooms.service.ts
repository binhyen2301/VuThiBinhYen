import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UtilitiesService } from "./utilities.service";
import { BaseService } from "./base.service";
import { environment } from "@environments/environment";
import { catchError } from "rxjs/internal/operators/catchError";
import { Pagination, User } from "../models";
import { map } from "rxjs/operators";
import { Classrooms } from "../models/classrooms";

@Injectable({
    providedIn: "root",
})
export class ClassroomsService extends BaseService {
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
                `${environment.apiUrl}/classroom/save`,
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
                `${environment.apiUrl}/classroom/put/${id}`,
                JSON.stringify(entity),
                { headers: this._sharedHeaders }
            )
            .pipe(catchError(this.handleError));
    }

    getDetail(id) {
        return this.http
            .get<Classrooms>(`${environment.apiUrl}/classroom/get/${id}`, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    getAllPaging(filter, pageIndex, pageSize) {
        let condition: string = "";
        if (filter != "" && filter != null) {
            condition += `(name~'*${filter}*' OR classCode~'*${filter}*')`;
        }
        if (condition != "") {
            return this.http
                .get<Pagination<Classrooms>>(
                    `${environment.apiUrl}/classroom/${environment.getPage}?page=${pageIndex}&size=${pageSize}&filter=${condition}`,
                    {
                        headers: this._sharedHeaders,
                    }
                )
                .pipe(
                    map((response: Pagination<Classrooms>) => {
                        return response;
                    }),
                    catchError(this.handleError)
                );
        }
        return this.http
            .get<Pagination<Classrooms>>(
                `${environment.apiUrl}/classroom/${environment.getPage}?page=${pageIndex}&size=${pageSize}`,
                {
                    headers: this._sharedHeaders,
                }
            )
            .pipe(
                map((response: Pagination<Classrooms>) => {
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    getGiangVien() {
        return this.http
            .get<Pagination<User>>(
                `${environment.apiUrl}/tbl-user/${environment.getPage}?filter=role.id:3`,
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
            .delete(environment.apiUrl + "/classroom/del/" + id, {
                headers: this._sharedHeaders,
            })
            .pipe(catchError(this.handleError));
    }

    getMenuByUser(userId: string) {
        return this.http
            .get<Function[]>(`${environment.apiUrl}/api/users/${userId}/menu`, {
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
            .get<string[]>(`${environment.apiUrl}/api/users/${userId}/roles`, {
                headers: this._sharedHeaders,
            })
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
                `${environment.apiUrl}/api/users/${userId}/roles`,
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
            .post(`${environment.apiUrl}/account/login`, objectLogin)
            .pipe(catchError(this.handleError));
    }
}
