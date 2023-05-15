import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { BaseService } from "./base.service";
import { Profile, User } from "../models";
import { UsersService } from "./users.services";
import { NotificationService } from "./notifications.service";
import { Result } from "../models/resutl";

@Injectable({
    providedIn: "root",
})
export class AuthService extends BaseService {
    setToken(token: string) {
        localStorage.setItem("token", btoa(token)); // mã hóa token trước khi lưu trữ
    }

    getToken() {
        const token = localStorage.getItem("token");
        if (token) {
            return "Bearer " + atob(token); // giải mã token trước khi trả về
        }
        return null;
    }

    clearToken() {
        localStorage.removeItem("token");
    }

    public user: any | null;
    public res: Result;

    constructor(private userService: UsersService) {
        super();

        // this.manager.getUser().then(user => {
        //   this.user = user;
        //   this._authNavStatusSource.next(this.isAuthenticated());
        // });
    }

    getResult(response: any): any {
        let res: any = {
            success: null,
            message: null,
        };
        if (response.success == true) {
            this.user = response.result;
            this.user.full_name = response.result["full_name"];
            this.setToken(this.user.token);

            res.success = true;
            res.message = "Đăng nhập thành công";
        } else {
            res.success = false;
            res.message = response.result;
        }
    }
    login(userName: string, passWord: string) {
        // return this.manager.signinRedirect();

        this.userService.login(userName, passWord).subscribe(
            (response: any) => {
                if (response.success == true) {
                    this.user = response.result;
                    this.user.full_name = response.result["full_name"];
                    this.setToken(this.user.token);

                    this.getResult(response);
                } else {
                    this.getResult(response);
                }
            },
            (error) => {}
        );
    }

    async completeAuthentication() {
        // this.user = await this.manager.signinRedirectCallback();
        // this._authNavStatusSource.next(this.isAuthenticated());
    }

    isAuthenticated(): boolean {
        return this.user != null;
    }

    get authorizationHeaderValue(): string {
        return this.getToken();
    }

    get name(): string {
        return this.user != null ? this.user.full_name : "";
    }

    // get profile(): Profile {
    //   return this.user != null ? this.user.profile : null;
    // }
    async signout() {
        // await this.manager.signoutRedirect();
    }
}
