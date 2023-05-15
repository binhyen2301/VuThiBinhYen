import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NotificationService } from "@app/shared/services";
import { SubjectService } from "@app/shared/services/subject.service";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { Subscription } from "rxjs";
import { SubjectDetailComponent } from "../subject-detail/subject-detail.component";
import { MessageConstants } from "@app/shared/constants";
import { SubjectImportExcellComponent } from "../subject-import-excell/subject-import-excell.component";
import { ChamdiemComponent } from "../chamdiem/chamdiem.component";
import { ClassroomsService } from "@app/shared/services/classrooms.service";
import { GroupService } from "@app/shared/services/group.service";
import { SubjectStudentService } from "@app/shared/services/subject-student.service";

@Component({
    selector: "app-subject-details-by-id",
    templateUrl: "./subject-details-by-id.component.html",
    styleUrls: ["./subject-details-by-id.component.scss"],
})
export class SubjectDetailsByIdComponent implements OnInit {
    private subscription: Subscription[] = [];
    public entityId: string;

    // Default
    public bsModalRef: BsModalRef;
    public blockedPanel = false;
    public blockedPanelRole = false;
    /**
     * Paging
     */
    public pageIndex = 0;
    public pageSize = 10;
    public pageDisplay = 10;
    public totalRecords: number;
    public keyword = "";
    public maSV = "";
    public hoTen = "";
    public classId = "";
    public groupId = "";

    // Users
    public items: any[];
    public selectedItems = [];
    public subject: any;
    public rootClass: any[];
    public rootGroup: any[];

    constructor(
        private activeRoute: ActivatedRoute,
        private modalService: BsModalService,
        private subjectService: SubjectService,
        private subjectStudentService: SubjectStudentService,
        private classroomsService: ClassroomsService,
        private groupService: GroupService,
        private notificationService: NotificationService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.subscription.push(
            this.activeRoute.params.subscribe((params) => {
                this.entityId = params["id"];
                console.log(this.entityId);
            })
        );
        this.loadData();
        this.loadClass();
        this.loadGroup();
        this.loadSubjectDetail(this.entityId);
    }

    showHideRoleTable() {
        // if (this.showRoleAssign) {
        //     if (this.selectedItems.length === 1) {
        //         this.loadUserRoles();
        //     }
        // }
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

    loadGroup() {
        this.groupService
            .getAllPaging("", null, 0, 10000)
            .subscribe((response: any) => {
                this.rootGroup = [];
                response.result.content.forEach((element) => {
                    this.rootGroup.push({
                        value: element.id,
                        label: element.tille + ` (${element.name})`,
                    });
                });
            });
    }

    loadData(selectionId = null) {
        console.log(
            this.maSV +
                "-" +
                this.hoTen +
                "-" +
                this.classId +
                "-" +
                this.groupId
        );
        this.blockedPanel = true;
        this.subjectService
            .getAllStudentInSubject(
                this.maSV,
                this.hoTen,
                this.classId,
                this.groupId,
                this.entityId,
                this.pageIndex,
                this.pageSize
            )
            .subscribe(
                (response: any) => {
                    this.items = response.result.content;
                    this.pageIndex = this.pageIndex;
                    this.pageSize = this.pageSize;
                    this.totalRecords = response.result.totalElements;
                    if (
                        this.selectedItems.length === 0 &&
                        this.items.length > 0
                    ) {
                        this.selectedItems.push(this.items[0]);
                    }
                    // Nếu có là sửa thì chọn selection theo Id
                    if (selectionId != null && this.items.length > 0) {
                        this.selectedItems = this.items.filter(
                            (x) => x.Id === selectionId
                        );
                    }

                    setTimeout(() => {
                        this.blockedPanel = false;
                    }, 1000);
                },
                (error) => {
                    setTimeout(() => {
                        this.blockedPanel = false;
                    }, 1000);
                }
            );
    }

    pageChanged(event: any): void {
        this.pageIndex = event.page;
        this.pageSize = event.rows;
        this.loadData();
    }

    onRowSelectAll() {
        // this.selectedRoleItems = [];
        // this.totalUserRoleRecords = 0;
        // this.userRoles = [];
    }

    onRowSelect(event) {}

    onRowUnselect(event) {}

    showAddModal() {
        const initialState = {
            entityId: this.entityId,
        };
        this.bsModalRef = this.modalService.show(SubjectImportExcellComponent, {
            initialState: initialState,
            class: "modal-lg",
            backdrop: "static",
        });

        this.bsModalRef.content.saved.subscribe((response) => {
            this.bsModalRef.hide();
            this.loadData(response.id);
        });
    }
    showModalChamDiem(id: any, file: any) {
        if (file == null || file == "") {
            this.notificationService.showError(
                MessageConstants.CHUA_NOP_BAI_TAP
            );
            return;
        }
        const initialState = {
            entityId: id,
        };
        this.bsModalRef = this.modalService.show(ChamdiemComponent, {
            initialState: initialState,
            class: "modal-lg",
            backdrop: "static",
        });

        this.bsModalRef.content.saved.subscribe((response) => {
            this.bsModalRef.hide();
            this.loadData(response.id);
        });
    }

    loadSubjectDetail(id: any) {
        this.subjectService.getDetail(id).subscribe(
            (response: any) => {
                this.subject = response.result;
                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            },
            (error) => {
                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            }
        );
    }

    ketThucHocPhan() {
        this.notificationService.showConfirmation(
            MessageConstants.KET_THUC_HOC_PHAN,
            () => {
                this.ketThucHocPhanHanlde(this.entityId);
            }
        );
    }

    ketThucHocPhanHanlde(id: any) {
        let count: number = 0;
        for (let key in this.items) {
            if (this.items[key].score != null) {
                count++;
            }
        }
        if (count < this.items.length) {
            this.notificationService.showError(
                "Không thể kết thúc học phần. Vui lòng cập nhật hết kết quả cho sinh viên !"
            );
            return;
        }
        this.subjectStudentService.ketThucHocPhanS(id).subscribe(
            () => {
                this.notificationService.showSuccess(
                    MessageConstants.DELETED_OK_MSG
                );
                this.redirect();
                this.selectedItems = [];
                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            },
            (error) => {
                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            }
        );
    }

    redirect(): void {
        this.router.navigateByUrl("/admin/subject");
    }

    status(date1: Date, date2: Date) {
        if (date1 > date2) {
            return "Nộp muộn";
        }
        return "";
    }
}
