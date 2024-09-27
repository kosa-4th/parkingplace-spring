package org.gomgom.parkingplace.enums;

public enum Bool {
    N,
    Y,
    C,
    D;

    public String getString() {
        switch (this) {
            case N:
                return "결제 대기";
            case C:
                return "결제 완료";
            case D:
                return "예약 취소";
            case Y:
                return "예약 확정";
            default:
                return "알 수 없는 상태"; // 예외 처리용 기본값
        }
    }
}
