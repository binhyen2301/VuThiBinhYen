import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { RolesAssignComponent } from "@app/protected-zone/systems/users/roles-assign/roles-assign.component";
import { UsersDetailComponent } from "@app/protected-zone/systems/users/users-detail/users-detail.component";
import { MessageConstants } from "@app/shared/constants";
import { NotificationService, UsersService } from "@app/shared/services";
import { StudentService } from "@app/shared/services/student/student.service";

import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";

@Component({
    selector: "app-sinh-vien-index",
    templateUrl: "./sinh-vien-index.component.html",
    styleUrls: ["./sinh-vien-index.component.scss"],
})
export class SinhVienIndexComponent implements OnInit {
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

    // Users
    public items: any[];
    public selectedItems = [];
    public selectedRoleItems = [];
    // Role
    public userRoles: any[] = [];
    public showRoleAssign = false;
    public totalUserRoleRecords: number;

    constructor(
        private modalService: BsModalService,
        private studentService: StudentService,
        private notificationService: NotificationService,
        private router: Router
    ) {}

    ngOnInit() {
        this.loadData();
    }

    loadData(selectionId = null) {
        this.blockedPanel = true;
        this.studentService
            .getAllPaging(this.keyword, this.pageIndex, this.pageSize)
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

                    // Load data grid 02

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
        this.pageIndex = event.page + 1;
        this.pageSize = event.rows;
        this.loadData();
    }

    onRowSelectAll() {
        this.selectedRoleItems = [];
        this.totalUserRoleRecords = 0;
        this.userRoles = [];
    }

    showAddModal() {
        this.bsModalRef = this.modalService.show(UsersDetailComponent, {
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

        this.router.navigateByUrl(
            "/admin/students/" + this.selectedItems[0].id
        );

        //     this.bsModalRef = this.modalService.show(UsersDetailComponent, {
        //         initialState: initialState,
        //         class: "modal-lg",
        //         backdrop: "static",
        //     });

        //     this.bsModalRef.content.saved.subscribe((response) => {
        //         this.bsModalRef.hide();
        //         this.loadData(response.id);
        //     });
    }

    deleteItems() {
        if (this.selectedItems.length === 0) {
            this.notificationService.showError(
                MessageConstants.NOT_CHOOSE_ANY_RECORD
            );
            return;
        }
        const id = this.selectedItems[0].id;
        this.notificationService.showConfirmation(
            MessageConstants.CONFIRM_DELETE_MSG,
            () => this.deleteItemsConfirm(id)
        );
    }

    moTaiKhoans() {
        if (this.selectedItems.length === 0) {
            this.notificationService.showError(
                MessageConstants.NOT_CHOOSE_ANY_RECORD
            );
            return;
        }
        const id = this.selectedItems[0].id;
        this.notificationService.showConfirmation(MessageConstants.MO_TK, () =>
            this.moTaiKhoan(id)
        );
    }

    moTaiKhoan(id: any) {
        this.blockedPanel = true;
        this.studentService.moTaiKhoan(id).subscribe(
            () => {
                this.notificationService.showSuccess(
                    MessageConstants.UPDATED_OK_MSG
                );
                this.loadData();
                this.selectedItems = [];

                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            },
            (error) => {
                this.notificationService.showError(error);
                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            }
        );
    }

    khoaTaiKhoans() {
        if (this.selectedItems.length === 0) {
            this.notificationService.showError(
                MessageConstants.NOT_CHOOSE_ANY_RECORD
            );
            return;
        }
        const id = this.selectedItems[0].id;
        this.notificationService.showConfirmation(
            MessageConstants.KHOA_TK,
            () => this.khoaTaiKhoan(id)
        );
    }

    khoaTaiKhoan(id: any) {
        this.blockedPanel = true;
        this.studentService.khoaTaiKhoan(id).subscribe(
            () => {
                this.notificationService.showSuccess(
                    MessageConstants.UPDATED_OK_MSG
                );
                this.loadData();
                this.selectedItems = [];

                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            },
            (error) => {
                this.notificationService.showError(error);
                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            }
        );
    }

    deleteItemsConfirm(ids: any[]) {
        this.blockedPanel = true;
        this.studentService.delete(ids).subscribe(
            () => {
                this.notificationService.showSuccess(
                    MessageConstants.DELETED_OK_MSG
                );
                this.loadData();
                this.selectedItems = [];

                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            },
            (error) => {
                this.notificationService.showError(error);

                setTimeout(() => {
                    this.blockedPanel = false;
                }, 1000);
            }
        );
    }
}
