import { Component, OnInit } from "@angular/core";
import { routerTransition } from "../router.animations";
import {
    AuthService,
    NotificationService,
    UsersService,
} from "../shared/services";
import { NgxSpinnerService } from "ngx-spinner";
import { Result } from "@app/shared/models";
import { Router } from "@angular/router";

@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.scss"],
    animations: [routerTransition()],
})
export class LoginComponent implements OnInit {
    constructor(
        private authService: AuthService,
        private notificationService: NotificationService,
        private userService: UsersService,
        private router: Router
    ) {}

    public username = "";
    public password = "";
    public user: any | null;
    ngOnInit() {}

    login() {
        // return this.manager.signinRedirect();

        this.userService.login(this.username, this.password).subscribe(
            (response: any) => {
                if (response.success == true) {
                    this.user = response.result;
                    this.authService.user = response.result;
                    this.authService.user.full_name =
                        response.result["full_name"];
                    this.authService.setToken(this.user.token);
                    this.notificationService.showSuccess(
                        "Đăng nhập thành công"
                    );
                    console.log("aaaaa" + this.authService.isAuthenticated());
                    this.router.navigateByUrl("/admin/dashboard");
                } else {
                    this.notificationService.showError(response.result);
                }
            },
            (error) => {}
        );
    }
}
