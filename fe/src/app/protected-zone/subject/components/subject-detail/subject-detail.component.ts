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
import { SubjectService } from "@app/shared/services/subject.service";
import { BsModalRef } from "ngx-bootstrap/modal";

@Component({
    selector: "app-subject-detail",
    templateUrl: "./subject-detail.component.html",
    styleUrls: ["./subject-detail.component.scss"],
})
export class SubjectDetailComponent implements OnInit {
    constructor(
        public bsModalRef: BsModalRef,
        private subjectService: SubjectService,
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
        code: [
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
        gvgdId: [
            {
                type: "required",
                message: "Vui lòng chọn giảng viên giảng dạy",
            },
        ],
        gv1Id: [
            {
                type: "required",
                message: "Vui lòng chọn giảng viên phụ trách",
            },
        ],
        gv2Id: [
            {
                type: "required",
                message: "Vui lòng chọn giảng viên phụ trách",
            },
        ],
        expireDate: [
            {
                type: "required",
                message: "Vui lòng chọn ngày hết hạn",
            },
        ],
    };

    ngOnInit() {
        this.entityForm = this.fb.group({
            id: new FormControl(""),
            code: new FormControl(
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
            gvgdId: new FormControl(
                "",
                Validators.compose([Validators.required])
            ),
            gv1Id: new FormControl(
                "",
                Validators.compose([Validators.required])
            ),
            gv2Id: new FormControl(
                "",
                Validators.compose([Validators.required])
            ),
            expireDate: new FormControl(),
        });
        this.loadGV();
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
        this.subjectService.getDetail(id).subscribe(
            (response: any) => {
                const dob: Date = new Date(response.result.expireDate);
                this.entityForm.setValue({
                    id: response.result.id,
                    code: response.result.code,
                    name: response.result.name,
                    gvgdId: response.result.gvgdId,
                    gv1Id: response.result.gv1Id,
                    gv2Id: response.result.gv2Id,
                    expireDate: dob,
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
        rawValues.dob = this.datePipe.transform(
            this.entityForm.controls["expireDate"].value,
            "yyyy-MM-dd"
        );
        if (this.entityId) {
            this.subjectService.update(this.entityId, rawValues).subscribe(
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
            this.subjectService.add(rawValues).subscribe(
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
