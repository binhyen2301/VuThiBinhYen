import { NgModule } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { GroupStudentIndexComponent } from "./group-student-index/group-student-index.component";
import { GroupStudentRoutingModule } from "./group-student-routing.module";
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
import { FileUploadModule } from "primeng/fileupload";
import { CheckboxModule } from "primeng/checkbox";
import { BsModalService, ModalModule } from "ngx-bootstrap/modal";
import { NotificationService } from "@app/shared/services";
import { ComponentLoaderFactory } from "ngx-bootstrap/component-loader";
import { PositioningService } from "ngx-bootstrap/positioning";
import { ClassroomsDetailComponent } from './classrooms-detail/classrooms-detail.component';
import { NopBaiComponent } from './nop-bai/nop-bai.component';
import { ImformationComponent } from './imformation/imformation.component';

@NgModule({
    declarations: [GroupStudentIndexComponent, ClassroomsDetailComponent, NopBaiComponent, ImformationComponent],
    imports: [
        CommonModule,
        GroupStudentRoutingModule,
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
export class GroupStudentModule {}
