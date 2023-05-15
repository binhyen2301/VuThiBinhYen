import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TranslateModule } from "@ngx-translate/core";

import { LoginRoutingModule } from "./login-routing.module";
import { LoginComponent } from "./login.component";
import { NgxSpinnerService } from "ngx-spinner";
import { NotificationService } from "@app/shared/services";
import { InputTextModule } from "primeng/inputtext";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

@NgModule({
    imports: [
        CommonModule,
        TranslateModule,
        LoginRoutingModule,
        InputTextModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    declarations: [LoginComponent],
    providers: [NgxSpinnerService, NotificationService],
})
export class LoginModule {}
