package com.haivn.enums;

public enum EHttpStatus {
    OK(200, "Thành công"),
    INCORRECT_INFORMATION(300, "Thông tin không chính xác"),

    ALREADY_EXISTS(301, "Thông tin đã tồn tại"),

    INVALID_INFORMATION(302, "Thông tin không hợp lệ"),
    LACK_INFORMATION(303, "Thiếu thông tin"),

    DISABLE_ACCOUNT(304, "Tài khoản bị khóa"),

//    NOT_ENOUGH_MONEY(305, "Không đủ số dư"),
//
//    NO_NUMBER(306, "Không có số"),

    ERROR_TIMEOUT(308, "Đã quá thời gian timeout"),

//    NOT_LIMIT(309, "Đã quá số lần thiết lập"),

//    NOT_DATE(310, "Sai định dạng ngày tháng"),

    SMS_DRAFT(311, "Lỗi máy chủ nội bộ"),

    STATUS_312(312, "message"),

    BAD_REQUEST(400, "Yêu cầu này bị lỗi"),
    UNAUTHORIZED(401, "Không có quyền"),
    FORBIDDEN(403, "Bị cấm truy cập"),
    METHOD_NOT_ALLOWED(405, "Phương thức không được phép"),
    UNSUPPORTED_MEDIA_TYPE(415, "Không hỗ trợ kiểu media này"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Phạm vi yêu cầu không thỏa mãn"),
    INTERNAL_SERVER_ERROR(500, "Lỗi máy chủ nội bộ");


    EHttpStatus(int value, String message) {
        this.code = value;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private final int code;
    private final String message;
}
