import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { GroupDetailComponent } from "@app/protected-zone/group/components/group-detail/group-detail.component";
import { ChamdiemComponent } from "@app/protected-zone/subject/components/chamdiem/chamdiem.component";
import { SubjectImportExcellComponent } from "@app/protected-zone/subject/components/subject-import-excell/subject-import-excell.component";
import { MessageConstants } from "@app/shared/constants";
import { NotificationService } from "@app/shared/services";
import { ClassroomsService } from "@app/shared/services/classrooms.service";
import { GroupService } from "@app/shared/services/group.service";
import { GroupStudentService } from "@app/shared/services/student/group-student.service";
import { SubjectStudentService } from "@app/shared/services/subject-student.service";
import { SubjectService } from "@app/shared/services/subject.service";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { Subscription } from "rxjs";
import { NopBaiComponent } from "../nop-bai/nop-bai.component";

@Component({
    selector: "app-group-student-index",
    templateUrl: "./group-student-index.component.html",
    styleUrls: ["./group-student-index.component.scss"],
})
export class GroupStudentIndexComponent implements OnInit {
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
        private groupStudentService: GroupStudentService,
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
        //this.loadSubjectDetail(this.entityId);
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
        this.groupStudentService
            .getAllPaging(
                null,
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

    showNopBai(id: any, group: any) {
        if (group == null || group == "") {
            this.notificationService.showError(
                MessageConstants.CHUA_DANG_KI_DE_TAI
            );
            return;
        }
        const initialState = {
            entityId: id,
        };
        this.bsModalRef = this.modalService.show(NopBaiComponent, {
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
        this.groupStudentService.getDetail(id).subscribe(
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

    redirect(id: any, studenSubjectId: any): void {
        this.router.navigateByUrl(
            "/student/classrooms/" + id + "/student-subject/" + studenSubjectId
        );
    }

    loadDetail(id: any) {
        this.router.navigateByUrl("/student/classrooms/");
    }
}
