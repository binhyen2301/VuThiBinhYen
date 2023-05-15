import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { SubjectDetailComponent } from "@app/protected-zone/subject/components/subject-detail/subject-detail.component";
import { MessageConstants } from "@app/shared/constants";
import { NotificationService } from "@app/shared/services";
import { GroupService } from "@app/shared/services/group.service";
import { SubjectService } from "@app/shared/services/subject.service";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { Subscription } from "rxjs";
import { GroupDetailComponent } from "../group-detail/group-detail.component";

@Component({
    selector: "app-group-index",
    templateUrl: "./group-index.component.html",
    styleUrls: ["./group-index.component.scss"],
})
export class GroupIndexComponent implements OnInit {
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

    constructor(
        private modalService: BsModalService,
        private groupService: GroupService,
        private notificationService: NotificationService,
        private router: Router
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
        this.groupService
            .getAllPaging(this.keyword, null, this.pageIndex, this.pageSize)
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
            this.groupService.delete(id).subscribe(
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

    redirect(id: any): void {
        this.router.navigateByUrl("/subject/" + id);
    }
}
