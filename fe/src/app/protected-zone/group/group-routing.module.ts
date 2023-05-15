import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { GroupModule } from "./group.module";
import { GroupIndexComponent } from "./components/group-index/group-index.component";

const routes: Routes = [
    {
        path: "",
        component: GroupIndexComponent,
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class GroupRoutingModule {}
