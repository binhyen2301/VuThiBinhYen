import { Component, OnInit } from "@angular/core";
import { RolesAssignComponent } from "@app/protected-zone/systems/users/roles-assign/roles-assign.component";
import { UsersDetailComponent } from "@app/protected-zone/systems/users/users-detail/users-detail.component";
import { MessageConstants } from "@app/shared/constants";
import { Pagination, User } from "@app/shared/models";
import { Classrooms } from "@app/shared/models/classrooms";
import { NotificationService, UsersService } from "@app/shared/services";
import { ClassroomsService } from "@app/shared/services/classrooms.service";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { ClassroomsDetailComponent } from "../classrooms-detail/classrooms-detail.component";
import { Subscription } from "rxjs";

@Component({
    selector: "app-classroom-index",
    templateUrl: "./classroom-index.component.html",
    styleUrls: ["./classroom-index.component.scss"],
})
export class ClassroomIndexComponent implements OnInit {
    private subscription = new Subscription();
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

    constructor(
        private modalService: BsModalService,
        private classroomService: ClassroomsService,
        private notificationService: NotificationService
    ) {}

    ngOnInit() {
        this.loadData();
    }

    showHideRoleTable() {
        // if (this.showRoleAssign) {
        //     if (this.selectedItems.length === 1) {
        //         this.loadUserRoles();
        //     }
        // }
    }

    loadData(selectionId = null) {
        this.blockedPanel = true;
        this.classroomService
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
        this.bsModalRef = this.modalService.show(ClassroomsDetailComponent, {
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
        this.bsModalRef = this.modalService.show(ClassroomsDetailComponent, {
            initialState: initialState,
            class: "modal-lg",
            backdrop: "static",
        });

        this.bsModalRef.content.saved.subscribe((response) => {
            this.bsModalRef.hide();
            this.loadData(response.id);
        });
    }

    deleteItems() {
        const id = this.selectedItems[0].id;
        this.notificationService.showConfirmation(
            MessageConstants.CONFIRM_DELETE_MSG,
            () => this.deleteItemsConfirm(id)
        );
    }
    deleteItemsConfirm(id) {
        this.blockedPanel = true;
        this.subscription.add(
            this.classroomService.delete(id).subscribe(
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
                    setTimeout(() => {
                        this.blockedPanel = false;
                    }, 1000);
                }
            )
        );
    }
    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
