import { DatePipe } from "@angular/common";
import { Component, EventEmitter, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { MessageConstants } from "@app/shared/constants";
import { NotificationService } from "@app/shared/services";
import { SubjectStudentService } from "@app/shared/services/subject-student.service";
import { SubjectService } from "@app/shared/services/subject.service";
import { BsModalRef } from "ngx-bootstrap/modal";
import { Subscription } from "rxjs";

@Component({
    selector: "app-nop-bai",
    templateUrl: "./nop-bai.component.html",
    styleUrls: ["./nop-bai.component.scss"],
})
export class NopBaiComponent implements OnInit {
    constructor(
        public bsModalRef: BsModalRef,
        private subjectStudentService: SubjectStudentService,
        private notificationService: NotificationService,
        private fb: FormBuilder,
        private datePipe: DatePipe
    ) {}

    public attachments: any[] = [];
    public selectedFiles: File[] = [];
    public blockedPanel = false;
    public myRoles: string[] = [];
    public entityForm: FormGroup;
    public dialogTitle: string;
    public entityId: string;
    public btnDisabled = false;
    saved: EventEmitter<any> = new EventEmitter();
    private subscription: Subscription[] = [];

    ngOnInit() {
        console.log(`aaaaaaaaaaaaaaa ${this.entityId}`);
        this.entityForm = this.fb.group({});
    }

    public removeAttachments($event) {
        if ($event.file) {
            this.selectedFiles.splice(
                this.selectedFiles.findIndex(
                    (item) => item.name === $event.file.name
                ),
                1
            );
        }
    }

    public selectAttachments($event) {
        if ($event.currentFiles) {
            $event.currentFiles.forEach((element) => {
                this.selectedFiles.push(element);
            });
        }
    }

    onUpload(event) {
        console.log("sssssssssssssssssssssss");
    }

    public saveChange() {
        this.btnDisabled = true;
        this.blockedPanel = true;
        const formData: FormData = new FormData();
        this.selectedFiles.forEach((file) => {
            formData.append("file", file, file.name);
        });

        console.log(this.entityId);
        this.subscription.push(
            this.subjectStudentService
                .nopBai(formData, this.entityId)
                .subscribe(
                    (response: any) => {
                        console.log(response.type);
                        if (response.type == 4) {
                            this.notificationService.showSuccess(
                                MessageConstants.UPLOAD_FILE_MSG_SUCCESS
                            );
                            this.saved.emit(this.entityForm.value);
                        }
                        setTimeout(() => {
                            this.btnDisabled = false;
                            this.blockedPanel = false;
                        }, 1000);
                    },
                    (error) => {
                        this.notificationService.showError(error);
                        setTimeout(() => {
                            this.btnDisabled = false;
                            this.blockedPanel = false;
                        }, 1000);
                    }
                )
        );
    }
    ngOnDestroy(): void {
        this.subscription.forEach((element) => {
            element.unsubscribe();
        });
    }
}
