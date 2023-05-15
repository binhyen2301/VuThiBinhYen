import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { GroupDetailComponent } from "@app/protected-zone/group/components/group-detail/group-detail.component";
import { MessageConstants } from "@app/shared/constants";
import { AuthService, NotificationService } from "@app/shared/services";
import { ClassroomsService } from "@app/shared/services/classrooms.service";
import { GroupService } from "@app/shared/services/group.service";
import { GroupStudentService } from "@app/shared/services/student/group-student.service";
import { SubjectStudentService } from "@app/shared/services/subject-student.service";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { Subscription } from "rxjs";

@Component({
    selector: "app-classrooms-detail",
    templateUrl: "./classrooms-detail.component.html",
    styleUrls: ["./classrooms-detail.component.scss"],
})
export class ClassroomsDetailComponent implements OnInit {
    private subscription: Subscription[] = [];
    // Default
    public entityId: string;
    public entityStudentSubjetctId: string;
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
    public userId: number;

    // Users
    public items: any[];
    public selectedItems = [];
    public itemSubjectStudent: any[];

    public studentSubject: any;
    //
    private subscriptionDelete = new Subscription();

    constructor(
        private modalService: BsModalService,
        private groupService: GroupService,
        private subjectStudentService: SubjectStudentService,
        private notificationService: NotificationService,
        private authService: AuthService,
        private activeRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.userId = this.authService.user.id;
        this.subscription.push(
            this.activeRoute.params.subscribe((params) => {
                this.entityId = params["id"];
                this.entityStudentSubjetctId = params["student-subject-id"];
                console.log(this.entityId);
                console.log(this.entityStudentSubjetctId);
            })
        );
        this.loadSubjectStudentData();
        this.loadData();
        this.loadDetailSubjectStudentData(this.entityStudentSubjetctId);
    }

    showHideRoleTable() {
        // if (this.showRoleAssign) {
        //     if (this.selectedItems.length === 1) {
        //         this.loadUserRoles();
        //     }
        // }
    }
    loadDetailSubjectStudentData(id: any) {
        this.subjectStudentService.getDetail(id).subscribe(
            (response: any) => {
                this.studentSubject = response.result;
            },
            (error) => {}
        );
    }

    loadSubjectStudentData(selectionId = null) {
        this.blockedPanel = true;

        this.groupService.getAllSubjectStudent(this.userId).subscribe(
            (response: any) => {
                this.itemSubjectStudent = response.result.content;
            },
            (error) => {}
        );
    }

    loadData(selectionId = null) {
        this.blockedPanel = true;
        this.groupService
            .getAllPaging(
                this.keyword,
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

    count(rowGroupId) {
        let count: number = 0;

        for (const key in this.itemSubjectStudent) {
            console.log("=================" + key);
            if (this.itemSubjectStudent[key].group != null) {
                if (
                    this.userId === this.itemSubjectStudent[key].students.id &&
                    rowGroupId === this.itemSubjectStudent[key].group.id
                ) {
                    count++;
                }
            }
        }
        return count;
    }

    check(rowGroupId, userId) {
        for (const key in this.itemSubjectStudent) {
            if (this.itemSubjectStudent[key].group != null) {
                if (
                    this.userId === this.itemSubjectStudent[key].students.id &&
                    rowGroupId === this.itemSubjectStudent[key].group.id
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    pageChanged(event: any): void {
        this.pageIndex = event.page;
        this.pageSize = event.rows;
        this.loadData();
    }

    getDetail() {}

    dangKiNhom(id: any) {
        let objectUpdate: any;
        if (this.count(id) > 5) {
            this.notificationService.showError(
                "Số lượng cho phép phải nhỏ hơn 5 thành viên"
            );
            return;
        }
        this.notificationService.showConfirmation(
            MessageConstants.DANG_KI_NHOM,
            () => {
                this.dangKiNhomConfirm(id);
            }
        );
    }
    dangKiNhomConfirm(id: any) {
        this.blockedPanel = true;

        this.studentSubject.groupId = id;
        this.subscriptionDelete.add(
            this.subjectStudentService
                .dangKiNhom(this.studentSubject.id, this.studentSubject)
                .subscribe(
                    () => {
                        this.notificationService.showSuccess(
                            MessageConstants.UPDATED_OK_MSG
                        );
                        this.loadSubjectStudentData();
                        this.loadData();
                        this.loadDetailSubjectStudentData(
                            this.entityStudentSubjetctId
                        );
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
                )
        );
    }

    onRowSelectAll() {
        // this.selectedRoleItems = [];
        // this.totalUserRoleRecords = 0;
        // this.userRoles = [];
    }

    onRowSelect(event) {}

    onRowUnselect(event) {}

    showAddModal() {
        this.bsModalRef = this.modalService.show(GroupDetailComponent, {
            class: "modal-lg",
            backdrop: "static",
        });
        this.bsModalRef.content.saved.subscribe(() => {
            this.bsModalRef.hide();
            this.loadData();
            this.selectedItems = [];
        });
    }
    showEditModal() {
        if (this.selectedItems.length === 0) {
            this.notificationService.showError(
                MessageConstants.NOT_CHOOSE_ANY_RECORD
            );
            return;
        }
        const initialState = {
            entityId: this.selectedItems[0].id,
        };
        this.bsModalRef = this.modalService.show(GroupDetailComponent, {
            initialState: initialState,
            class: "modal-lg",
            backdrop: "static",
        });

        this.bsModalRef.content.saved.subscribe((response) => {
            this.bsModalRef.hide();
            this.loadData(response.id);
        });
    }
}
