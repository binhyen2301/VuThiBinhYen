import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { GroupStudentIndexComponent } from "./group-student-index/group-student-index.component";
import { ClassroomsDetailComponent } from "./classrooms-detail/classrooms-detail.component";
import { ImformationComponent } from "./imformation/imformation.component";

const routes: Routes = [
    {
        path: "",
        component: GroupStudentIndexComponent,
    },
    {
        path: ":id/student-subject/:student-subject-id",
        component: ClassroomsDetailComponent,
    },
    {
        path: "imformation",
        component: ImformationComponent,
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class GroupStudentRoutingModule {}
