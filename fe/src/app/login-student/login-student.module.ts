import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LoginStudentComponent } from "./login-student/login-student.component";
import { TranslateModule } from "@ngx-translate/core";
import { LoginRoutingModule } from "@app/login/login-routing.module";
import { InputTextModule } from "primeng/inputtext";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgxSpinnerService } from "ngx-spinner";
import { NotificationService } from "@app/shared/services";
import { LoginStudentRoutingModule } from "./login-student-routing.module";

@NgModule({
    declarations: [LoginStudentComponent],
    imports: [
        CommonModule,
        TranslateModule,
        LoginStudentRoutingModule,
        InputTextModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    providers: [NgxSpinnerService, NotificationService],
})
export class LoginStudentModule {}
