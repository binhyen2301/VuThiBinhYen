import { NgModule } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";

import { SinhVienIndexComponent } from "./sinh-vien-index/sinh-vien-index.component";
import { SinhVienRoutingModule } from "./sinh-vien-routing.module";
import { PanelModule } from "primeng/panel";
import { ButtonModule } from "primeng/button";
import { TableModule } from "primeng/table";
import { PaginatorModule } from "primeng/paginator";
import { BlockUIModule } from "primeng/blockui";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { InputTextModule } from "primeng/inputtext";
import { ProgressSpinnerModule } from "primeng/progressspinner";
import { ValidationMessageModule } from "@app/shared/modules/validation-message/validation-message.module";
import { KeyFilterModule } from "primeng/keyfilter";
import { CalendarModule } from "primeng/calendar";
import { CheckboxModule } from "primeng/checkbox";
import { BsModalService, ModalModule } from "ngx-bootstrap/modal";
import { NotificationService } from "@app/shared/services";
import { ComponentLoaderFactory } from "ngx-bootstrap/component-loader";
import { PositioningService } from "ngx-bootstrap/positioning";
import { SinhVienDetailComponent } from "./sinh-vien-detail/sinh-vien-detail.component";
import { FileUploadModule } from "primeng/fileupload";

@NgModule({
    declarations: [SinhVienIndexComponent, SinhVienDetailComponent],
    imports: [
        CommonModule,
        SinhVienRoutingModule,
        PanelModule,
        ButtonModule,
        TableModule,
        PaginatorModule,
        BlockUIModule,
        FormsModule,
        InputTextModule,
        ReactiveFormsModule,
        ProgressSpinnerModule,
        ValidationMessageModule,
        KeyFilterModule,
        CalendarModule,
        FileUploadModule,
        CheckboxModule,
        ModalModule.forRoot(),
    ],
    providers: [
        NotificationService,
        BsModalService,
        ComponentLoaderFactory,
        PositioningService,
        DatePipe,
    ],
})
export class SinhVienModule {}
