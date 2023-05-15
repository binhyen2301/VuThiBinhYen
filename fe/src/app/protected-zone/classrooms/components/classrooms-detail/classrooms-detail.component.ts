import { DatePipe } from "@angular/common";
import { Component, EventEmitter, OnInit } from "@angular/core";
import {
    FormBuilder,
    FormControl,
    FormGroup,
    Validators,
} from "@angular/forms";
import { MessageConstants } from "@app/shared/constants";
import { NotificationService, UsersService } from "@app/shared/services";
import { ClassroomsService } from "@app/shared/services/classrooms.service";
import { BsModalRef } from "ngx-bootstrap/modal";

@Component({
    selector: "app-classrooms-detail",
    templateUrl: "./classrooms-detail.component.html",
    styleUrls: ["./classrooms-detail.component.scss"],
})
export class ClassroomsDetailComponent implements OnInit {
    constructor(
        public bsModalRef: BsModalRef,
        private classroomsService: ClassroomsService,
        private notificationService: NotificationService,
        private fb: FormBuilder,
        private datePipe: DatePipe
    ) {}
    public blockedPanel = false;
    public myRoles: string[] = [];
    public entityForm: FormGroup;
    public dialogTitle: string;
    public entityId: string;

    public btnDisabled = false;
    public saveBtnName: string;
    public closeBtnName: string;
    public vi: any;
    saved: EventEmitter<any> = new EventEmitter();

    public rootGV: any[] = [];

    // Validate
    noSpecial: RegExp = /^[^<>*!_~]+$/;
    validation_messages = {
        classCode: [
            {
                type: "required",
                message: "Bạn phải nhập mã lớp",
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
        name: [
            {
                type: "required",
                message: "Bạn phải nhập tên lớp",
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
        teacherId: [
            {
                type: "required",
                message: "Vui lòng chọn giảng viên phụ trách",
            },
        ],
    };

    ngOnInit() {
        this.entityForm = this.fb.group({
            id: new FormControl(""),
            classCode: new FormControl(
                "",
                Validators.compose([
                    Validators.required,
                    Validators.maxLength(255),
                    Validators.minLength(3),
                ])
            ),
            name: new FormControl(
                "",
                Validators.compose([
                    Validators.required,
                    Validators.maxLength(255),
                    Validators.minLength(3),
                ])
            ),
            teacherId: new FormControl(
                "",
                Validators.compose([Validators.required])
            ),
        });
        this.loadGV();
        if (this.entityId) {
            this.loadClassDetail(this.entityId);
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

    loadClassDetail(id: any) {
        this.btnDisabled = true;
        this.blockedPanel = true;
        this.classroomsService.getDetail(id).subscribe(
            (response: any) => {
                // const dob: Date = new Date(response.dob);
                this.entityForm.setValue({
                    id: response.result.id,
                    classCode: response.result.classCode,
                    name: response.result.name,
                    teacherId: response.result.teacherId,
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
        this.classroomsService.getGiangVien().subscribe((response: any) => {
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

        if (this.entityId) {
            this.classroomsService.update(this.entityId, rawValues).subscribe(
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
            this.classroomsService.add(rawValues).subscribe(
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
