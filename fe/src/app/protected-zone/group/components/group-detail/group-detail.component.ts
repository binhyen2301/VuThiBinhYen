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
import { GroupService } from "@app/shared/services/group.service";
import { SubjectService } from "@app/shared/services/subject.service";
import { BsModalRef } from "ngx-bootstrap/modal";

@Component({
    selector: "app-group-detail",
    templateUrl: "./group-detail.component.html",
    styleUrls: ["./group-detail.component.scss"],
})
export class GroupDetailComponent implements OnInit {
    constructor(
        public bsModalRef: BsModalRef,
        private groupService: GroupService,
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

    public rootSj: any[] = [];

    // Validate
    noSpecial: RegExp = /^[^<>*!_~]+$/;
    validation_messages = {
        name: [
            {
                type: "required",
                message: "Bạn phải nhập tên nhóm",
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
        title: [
            {
                type: "required",
                message: "Bạn phải nhập đề tài",
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
        subjectId: [
            {
                type: "required",
                message: "Vui lòng chọn môn học cần tạo nhóm",
            },
        ],
    };

    ngOnInit() {
        this.entityForm = this.fb.group({
            id: new FormControl(""),
            name: new FormControl(
                "",
                Validators.compose([
                    Validators.required,
                    Validators.maxLength(255),
                    Validators.minLength(3),
                ])
            ),
            tille: new FormControl(
                "",
                Validators.compose([
                    Validators.required,
                    Validators.maxLength(255),
                    Validators.minLength(3),
                ])
            ),
            subjectId: new FormControl(
                "",
                Validators.compose([Validators.required])
            ),
        });
        this.loadSJ();
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
        this.groupService.getDetail(id).subscribe(
            (response: any) => {
                // const dob: Date = new Date(response.dob);
                this.entityForm.setValue({
                    id: response.result.id,
                    name: response.result.name,
                    tille: response.result.tille,
                    subjectId: response.result.subjectId,
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
    loadSJ() {
        this.subjectService.getAllPagingByUser().subscribe((response: any) => {
            this.rootSj = [];
            response.result.content.forEach((element) => {
                this.rootSj.push({
                    value: element.id,
                    label: element.name + ` (${element.code})`,
                });
            });
        });
    }

    saveChange() {
        this.btnDisabled = true;
        this.blockedPanel = true;
        const rawValues = this.entityForm.getRawValue();

        if (this.entityId) {
            this.groupService.update(this.entityId, rawValues).subscribe(
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
            this.groupService.add(rawValues).subscribe(
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
