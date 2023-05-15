import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { SubjectIndexComponent } from "./components/subject-index/subject-index.component";
import { SubjectDetailsByIdComponent } from "./components/subject-details-by-id/subject-details-by-id.component";

const routes: Routes = [
    {
        path: "",
        component: SubjectIndexComponent,
    },
    {
        path: ":id",
        component: SubjectDetailsByIdComponent,
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class SubjectRoutingModule {}
