import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AppComponent } from "./app.component";
// import { AuthGuard } from './shared';

const routes: Routes = [
    {
        path: "",
        loadChildren: () =>
            import("./protected-zone/layout.module").then(
                (m) => m.ProtectedZoneModule
            ),
    },
    {
        path: "login",
        loadChildren: () =>
            import("./login/login.module").then((m) => m.LoginModule),
    },
    {
        path: "student-login",
        loadChildren: () =>
            import("./login-student/login-student.module").then(
                (m) => m.LoginStudentModule
            ),
    },
    {
        path: "auth-callback",
        loadChildren: () =>
            import("./auth-callback/auth-callback.module").then(
                (m) => m.AuthCallbackModule
            ),
    },
    {
        path: "error",
        loadChildren: () =>
            import("./server-error/server-error.module").then(
                (m) => m.ServerErrorModule
            ),
    },
    {
        path: "access-denied",
        loadChildren: () =>
            import("./access-denied/access-denied.module").then(
                (m) => m.AccessDeniedModule
            ),
    },
    {
        path: "not-found",
        loadChildren: () =>
            import("./not-found/not-found.module").then(
                (m) => m.NotFoundModule
            ),
    },
    { path: "**", redirectTo: "not-found" },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
