import { NgModule } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { SubjectIndexComponent } from "./components/subject-index/subject-index.component";
import { SubjectRoutingModule } from "./subject-routing.module";
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
import { SubjectDetailComponent } from "./components/subject-detail/subject-detail.component";
import { SubjectDetailsByIdComponent } from "./components/subject-details-by-id/subject-details-by-id.component";
import { SubjectImportExcellComponent } from "./components/subject-import-excell/subject-import-excell.component";
import { FileUploadModule } from "primeng";
import { ChamdiemComponent } from "./components/chamdiem/chamdiem.component";

@NgModule({
    declarations: [
        SubjectIndexComponent,
        SubjectDetailComponent,
        SubjectDetailsByIdComponent,
        SubjectImportExcellComponent,
        ChamdiemComponent,
    ],
    imports: [
        CommonModule,
        SubjectRoutingModule,
        PanelModule,
        ButtonModule,
        TableModule,
        PaginatorModule,
        BlockUIModule,
        FormsModule,
        InputTextModule,
        CalendarModule,
        ReactiveFormsModule,
        ProgressSpinnerModule,
        ValidationMessageModule,
        KeyFilterModule,
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
export class SubjectModule {}
