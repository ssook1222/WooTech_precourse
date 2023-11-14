package christmas;

import java.time.LocalDate;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");

        // 1. 날짜 및 메뉴 입력 검증
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        int date = scanner.nextInt();
        boolean isDateValid = DateValidator.validateDate(date);
        LocalDate orderDate = LocalDate.of(2023, 12, date);

        // 개행 문자 소비
        scanner.nextLine();

        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1): ");
        String orderInput = scanner.nextLine();

        // 콤마(,)를 기준으로 문자열을 나누어 배열에 저장
        String[] orderItems = orderInput.split(",");
        boolean isMenuValid = DateValidator.validateMenu(orderItems);

        // 주문 메뉴와 수량을 저장할 배열 초기화
        String[] orderedItems = new String[orderItems.length];
        int[] quantities = new int[orderItems.length];

        // 주문 메뉴와 수량을 파싱하여 배열에 저장
        for (int i = 0; i < orderItems.length; i++) {
            String[] menuAndQuantity = orderItems[i].split("-");

            // 올바른 형식이 아니면 에러 메시지 출력 후 프로그램 종료
            if (menuAndQuantity.length != 2) {
                System.out.println("[ERROR] 입력 형식이 올바르지 않습니다.");
            }

            orderedItems[i] = menuAndQuantity[0];
            quantities[i] = Integer.parseInt(menuAndQuantity[1]);
        }
        System.out.println("12월 " + date + "일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n");

        if (isDateValid && isMenuValid) {
            // 2. 주문 정보 출력
            OrderInformation.displayOrderInfo(orderedItems, quantities);

            // 3. 할인 및 혜택 계산
            double totalPrice = DiscountCalculator.calculateTotalPrice(orderedItems, quantities);
            double discount = DiscountCalculator.calculateDiscount(totalPrice, orderDate);
            double finalPrice = DiscountCalculator.calculateFinalPrice(totalPrice, discount);

            // 4. 증정 메뉴 확인
            String freeItem = FreeItemChecker.checkFreeItem(totalPrice);

            // 5. 혜택 내역 확인
            String benefitInfo = BenefitChecker.checkBenefits(totalPrice, orderDate);

            // 6. 이벤트 배지 확인
            double totalBenefits = totalPrice - finalPrice;
            String eventBadge = EventBadgeChecker.checkEventBadge(totalBenefits);

            // 7. 결과 출력
            System.out.println("<할인 전 총주문 금액>");
            System.out.println(totalPrice + "원\n");

            if (!freeItem.isEmpty()) {
                System.out.println("<증정 메뉴>");
                System.out.println(freeItem + '\n');
            }

            System.out.println("<혜택 내용>");
            System.out.println(benefitInfo + '\n');

            if (discount > 0) {
                System.out.println("<총할인 금액>");
                System.out.println("-" + discount + "원\n");
            }

            System.out.println("<할인 후 예상 결제 금액>");
            System.out.println(finalPrice + "원\n");

            if (!eventBadge.isEmpty()) {
                System.out.println("<12월 이벤트 배지>");
                System.out.println(eventBadge);
            }
        } else {
            System.out.println("[ERROR] 입력이 유효하지 않습니다");
        }
        scanner.close();
    }
}
