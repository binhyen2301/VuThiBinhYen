import { DatePipe } from "@angular/common";
import { Component, EventEmitter, OnInit } from "@angular/core";
import {
    FormBuilder,
    FormControl,
    FormGroup,
    Validators,
} from "@angular/forms";
import { MessageConstants } from "@app/shared/constants";
import { NotificationService } from "@app/shared/services";
import { SubjectStudentService } from "@app/shared/services/subject-student.service";
import { SubjectService } from "@app/shared/services/subject.service";
import { BsModalRef } from "ngx-bootstrap/modal";

@Component({
    selector: "app-chamdiem",
    templateUrl: "./chamdiem.component.html",
    styleUrls: ["./chamdiem.component.scss"],
})
export class ChamdiemComponent implements OnInit {
    constructor(
        public bsModalRef: BsModalRef,
        private subjectService: SubjectService,
        private subjectStudentService: SubjectStudentService,
        private notificationService: NotificationService,
        private fb: FormBuilder,
        private datePipe: DatePipe
    ) {}
    public blockedPanel = false;
    public myRoles: string[] = [];
    public entityForm: FormGroup;
    public dialogTitle: string;
    public entityId: string;
    public objectUpdate: any;
    public btnDisabled = false;
    public saveBtnName: string;
    public closeBtnName: string;
    public vi: any;
    saved: EventEmitter<any> = new EventEmitter();

    public rootGV: any[] = [];

    // Validate
    noSpecial: RegExp = /^[^<>*!_~]+$/;
    validation_messages = {
        score: [
            {
                type: "required",
                message: "Bạn phải nhập điểm",
            },
            {
                type: "minlength",
                message: "Bạn phải nhập ít nhất 3 kí tự",
            },
            {
                type: "maxlength",
                message: "Bạn không được nhập quá 255 kí tự",
            },
        ],
        content: [
            {
                type: "required",
                message: "Bạn phải nhập nhận xét",
            },
            {
                type: "minlength",
                message: "Bạn phải nhập ít nhất 3 kí tự",
            },
            {
                type: "maxlength",
                message: "Bạn không được nhập quá 255 kí tự",
            },
        ],
    };

    ngOnInit() {
        this.entityForm = this.fb.group({
            id: new FormControl(""),
            score: new FormControl(
                "",
                Validators.compose([
                    Validators.required,
                    Validators.maxLength(255),
                    Validators.minLength(3),
                ])
            ),
            content: new FormControl(
                "",
                Validators.compose([
                    Validators.required,
                    Validators.maxLength(255),
                    Validators.minLength(3),
                ])
            ),
        });
        if (this.entityId) {
            this.loadSubjectDetail(this.entityId);
            this.dialogTitle = "Cập nhật";
        } else {
            this.dialogTitle = "Thêm mới";
        }

        this.vi = {
            firstDayOfWeek: 0,
            dayNames: [
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
            ],
            dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
            dayNamesMin: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"],
            monthNames: [
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December",
            ],
            monthNamesShort: [
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec",
            ],
            today: "Today",
            clear: "Clear",
        };
    }

    loadSubjectDetail(id: any) {
        this.btnDisabled = true;
        this.blockedPanel = true;
        this.subjectStudentService.getDetail(id).subscribe(
            (response: any) => {
                // const dob: Date = new Date(response.dob);
                this.objectUpdate = response.result;
                this.entityForm.setValue({
                    id: response.result.id,
                    score: response.result.score,
                    content: response.result.content,
                });
                setTimeout(() => {
                    this.btnDisabled = false;
                    this.blockedPanel = false;
                }, 1000);
            },
            (error) => {
                setTimeout(() => {
                    this.btnDisabled = false;
                    this.blockedPanel = false;
                }, 1000);
            }
        );
    }
    loadGV() {
        this.subjectService.getGiangVien().subscribe((response: any) => {
            this.rootGV = [];
            response.result.content.forEach((element) => {
                this.rootGV.push({
                    value: element.id,
                    label: element.fullName + ` (${element.teacher_code})`,
                });
            });
        });
    }

    saveChange() {
        this.btnDisabled = true;
        this.blockedPanel = true;

        const rawValues = this.entityForm.getRawValue();
        this.objectUpdate.content = rawValues.content;
        this.objectUpdate.score = rawValues.score;

        if (this.entityId) {
            this.subjectStudentService
                .chamDiem(this.entityId, this.objectUpdate)
                .subscribe(
                    () => {
                        this.notificationService.showSuccess(
                            MessageConstants.UPDATED_OK_MSG
                        );

                        setTimeout(() => {
                            this.btnDisabled = false;
                            this.blockedPanel = false;
                        }, 1000);
                        this.saved.emit(this.entityForm.value);
                    },
                    (error) => {
                        this.notificationService.showError(error);
                        setTimeout(() => {
                            this.btnDisabled = false;
                            this.blockedPanel = false;
                        }, 1000);
                    }
                );
        } else {
            this.subjectStudentService.add(rawValues).subscribe(
                () => {
                    this.notificationService.showSuccess(
                        MessageConstants.CREATED_OK_MSG
                    );
                    this.saved.emit(this.entityForm.value);

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
            );
        }
    }
}
