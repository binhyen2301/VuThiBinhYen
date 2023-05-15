import { NgModule } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { ClassroomIndexComponent } from "./components/classroom-index/classroom-index.component";
import { ClassroomsRoutingModule } from "./classrooms-routing.module";
import { NotificationService } from "@app/shared/services";
import { BsModalService, ModalModule } from "ngx-bootstrap/modal";
import { ComponentLoaderFactory } from "ngx-bootstrap/component-loader";
import { PositioningService } from "ngx-bootstrap/positioning";
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
import { ClassroomsDetailComponent } from "./components/classrooms-detail/classrooms-detail.component";

@NgModule({
    declarations: [ClassroomIndexComponent, ClassroomsDetailComponent],
    imports: [
        CommonModule,
        ClassroomsRoutingModule,
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
export class ClassroomsModule {}
