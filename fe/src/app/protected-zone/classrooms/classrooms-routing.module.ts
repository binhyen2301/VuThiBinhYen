import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { ClassroomIndexComponent } from "./components/classroom-index/classroom-index.component";

const routes: Routes = [
    {
        path: "",
        component: ClassroomIndexComponent,
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class ClassroomsRoutingModule {}
