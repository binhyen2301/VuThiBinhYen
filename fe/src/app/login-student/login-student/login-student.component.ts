import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import {
    AuthService,
    NotificationService,
    UsersService,
} from "@app/shared/services";
import { StudentService } from "@app/shared/services/student/student.service";

@Component({
    selector: "app-login-student",
    templateUrl: "./login-student.component.html",
    styleUrls: ["./login-student.component.scss"],
})
export class LoginStudentComponent implements OnInit {
    constructor(
        private authService: AuthService,
        private notificationService: NotificationService,
        private studentService: StudentService,
        private router: Router
    ) {}

    public username = "";
    public password = "";
    public user: any | null;
    ngOnInit() {}

    login() {
        // return this.manager.signinRedirect();

        this.studentService.login(this.username, this.password).subscribe(
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
                    this.router.navigateByUrl("/student/dashboard");
                } else {
                    this.notificationService.showError(response.result);
                }
            },
            (error) => {}
        );
    }
}
