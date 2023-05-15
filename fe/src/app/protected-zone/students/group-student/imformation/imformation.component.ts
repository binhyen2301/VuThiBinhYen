import { Component, OnInit } from "@angular/core";
import {
    FormBuilder,
    FormControl,
    FormGroup,
    Validators,
} from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { MessageConstants } from "@app/shared/constants";
import {
    AuthService,
    NotificationService,
    UtilitiesService,
} from "@app/shared/services";
import { ClassroomsService } from "@app/shared/services/classrooms.service";
import { StudentService } from "@app/shared/services/student/student.service";
import { SelectItem } from "primeng/api";
import { Subscription } from "rxjs";

@Component({
    selector: "app-imformation",
    templateUrl: "./imformation.component.html",
    styleUrls: ["./imformation.component.scss"],
})
export class ImformationComponent implements OnInit {
    constructor(
        private notificationService: NotificationService,
        private utilitiesService: UtilitiesService,
        private studentService: StudentService,
        private activeRoute: ActivatedRoute,
        private authService: AuthService,
        private router: Router,
        private fb: FormBuilder,
        private classroomsService: ClassroomsService
    ) {}
    private subscription: Subscription[] = [];
    public entityForm: FormGroup;
    public dialogTitle: string;
    public entityId: string;
    public categories: SelectItem[] = [];
    public blockedPanel = false;
    public selectedFiles: File[] = [];
    public attachments: any[] = [];
    public vi: any;
    public imgage: any;
    public rootClass: any[];

    ngOnInit() {
        debugger;
        this.entityId = this.authService.user.id;
        this.entityForm = this.fb.group({
            id: new FormControl(""),
            name: new FormControl(
                "",
                Validators.compose([Validators.required])
            ),
            password: new FormControl(
                "",
                Validators.compose([Validators.required])
            ),
            fullName: new FormControl(
                "",
                Validators.compose([Validators.required])
            ),
            studentCode: new FormControl(""),
            classId: new FormControl(""),
        });
        this.entityForm.controls["studentCode"].disable({ onlySelf: true });
        this.entityForm.controls["name"].disable({ onlySelf: true });
        this.loadClass();
        if (this.entityId) {
            this.loadStudentDetail(this.entityId);
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

    loadClass() {
        this.classroomsService
            .getAllPaging("", 0, 10000)
            .subscribe((response: any) => {
                this.rootClass = [];
                response.result.content.forEach((element) => {
                    this.rootClass.push({
                        value: element.id,
                        label: element.name + ` (${element.classCode})`,
                    });
                });
            });
    }

    public selectAttachments($event) {
        if ($event.currentFiles) {
            $event.currentFiles.forEach((element) => {
                this.selectedFiles.push(element);
            });
        }
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

    loadStudentDetail(id: any) {
        this.studentService.getDetail(id).subscribe(
            (response: any) => {
                this.entityForm.setValue({
                    id: response.result.id,
                    name: response.result.name,
                    password: response.result.password,
                    fullName: response.result.fullName,
                    studentCode: response.result.studentCode,
                    classId: response.result.classId,
                    // dob: response.result.dob,
                });
                this.imgage = response.result.urlImage;
                setTimeout(() => {}, 1000);
            },
            (error) => {
                setTimeout(() => {}, 1000);
            }
        );
    }

    goBackToList() {
        this.router.navigateByUrl("/admin/dashboard");
    }
    public saveChange() {
        const formValues = this.entityForm.getRawValue();
        const formData = this.utilitiesService.ToFormData(formValues);

        this.selectedFiles.forEach((file) => {
            formData.append("file", file, file.name);
        });

        this.subscription.push(
            this.studentService.update(this.entityId, formData).subscribe(
                (response: any) => {
                    debugger;
                    if (response.type == 4) {
                        this.notificationService.showSuccess(
                            MessageConstants.UPDATED_OK_MSG
                        );
                        this.router.navigateByUrl("/admin/dashboard");
                    }
                },
                (error) => {
                    setTimeout(() => {
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
