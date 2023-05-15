import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { SinhVienIndexComponent } from "./sinh-vien-index/sinh-vien-index.component";
import { SinhVienDetailComponent } from "./sinh-vien-detail/sinh-vien-detail.component";

const routes: Routes = [
    {
        path: "",
        component: SinhVienIndexComponent,
    },
    {
        path: ":id",
        component: SinhVienDetailComponent,
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class SinhVienRoutingModule {}
