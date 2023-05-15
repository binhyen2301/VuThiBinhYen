import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LayoutComponent } from "./layout.component";
import { AuthGuard } from "@app/shared/guard/auth.guard";

const routes: Routes = [
    {
        path: "admin",
        component: LayoutComponent,
        canActivate: [AuthGuard],
        children: [
            {
                path: "dashboard",
                loadChildren: () =>
                    import("./dashboard/dashboard.module").then(
                        (m) => m.DashboardModule
                    ),
            },
            {
                path: "classroom",
                loadChildren: () =>
                    import("./classrooms/classrooms.module").then(
                        (m) => m.ClassroomsModule
                    ),
            },
            {
                path: "subject",
                loadChildren: () =>
                    import("./subject/subject.module").then(
                        (m) => m.SubjectModule
                    ),
            },
            {
                path: "students",
                loadChildren: () =>
                    import("./sinh-vien/sinh-vien.module").then(
                        (m) => m.SinhVienModule
                    ),
            },
            {
                path: "group",
                loadChildren: () =>
                    import("./group/group.module").then((m) => m.GroupModule),
            },
            {
                path: "contents",
                loadChildren: () =>
                    import("./contents/contents.module").then(
                        (m) => m.ContentsModule
                    ),
            },
            {
                path: "systems",
                loadChildren: () =>
                    import("./systems/systems.module").then(
                        (m) => m.SystemsModule
                    ),
            },
            {
                path: "statistics",
                loadChildren: () =>
                    import("./statistics/statistics.module").then(
                        (m) => m.StatisticsModule
                    ),
            },
        ],
    },
    {
        path: "student",
        component: LayoutComponent,
        canActivate: [AuthGuard],
        children: [
            {
                path: "dashboard",
                loadChildren: () =>
                    import("./dashboard/dashboard.module").then(
                        (m) => m.DashboardModule
                    ),
            },
            {
                path: "classrooms",
                loadChildren: () =>
                    import(
                        "./students/group-student/group-student.module"
                    ).then((m) => m.GroupStudentModule),
            },
        ],
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class LayoutRoutingModule {}
