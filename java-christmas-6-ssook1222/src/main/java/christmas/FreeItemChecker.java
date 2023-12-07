package christmas;

public class FreeItemChecker {
    public static String checkFreeItem(double totalPrice) {
        if (totalPrice >= 120000) {
            return "샴페인"; // 총주문 금액이 12만 원 이상이면 샴페인 증정
        } else {
            return "없음"; // 그 외의 경우는 증정 메뉴 없음
        }
    }
}
