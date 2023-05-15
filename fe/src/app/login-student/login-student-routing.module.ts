import { NgModule } from "@angular/core";

import { RouterModule, Routes } from "@angular/router";
import { LoginStudentComponent } from "./login-student/login-student.component";

const routes: Routes = [
    {
        path: "",
        component: LoginStudentComponent,
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class LoginStudentRoutingModule {}
