package christmas;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DiscountCalculator {
    // 각 메뉴별 가격 정보
    private static final Map<String, Double> menuPrices = new HashMap<>();

    static {
        // 메뉴별 가격 정보 초기화
        menuPrices.put("양송이수프", 6000.0);
        menuPrices.put("타파스", 5500.0);
        menuPrices.put("시저샐러드", 8000.0);
        menuPrices.put("티본스테이크", 55000.0);
        menuPrices.put("바비큐립", 54000.0);
        menuPrices.put("해산물파스타", 35000.0);
        menuPrices.put("크리스마스파스타", 25000.0);
        menuPrices.put("초코케이크", 15000.0);
        menuPrices.put("아이스크림", 5000.0);
        menuPrices.put("제로콜라", 3000.0);
        menuPrices.put("레드와인", 60000.0);
        menuPrices.put("샴페인", 25000.0);
    }

    public static double calculateTotalPrice(String[] orderedItems, int[] quantities) {
        double total = 0.0;
        for (int i = 0; i < orderedItems.length; i++) {
            String menuItem = orderedItems[i];
            int quantity = quantities[i];
            total += menuPrices.getOrDefault(menuItem, 0.0) * quantity;
        }
        return total;
    }

    // 크리스마스 디데이 할인
    public static double calculateChristmasDiscount(int dayOfMonth) {
        // 12월 1일부터 25일까지의 크리스마스 디데이 할인
        int baseDiscount = 1000;
        int additionalDiscount = 100 * (dayOfMonth - 1);
        return baseDiscount + additionalDiscount;
    }

    // 평일 할인
    public static double calculateWeekdayDiscount(boolean isWeekday) {
        // 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인
        return isWeekday ? 2023 : 0;
    }

    // 주말 할인
    public static double calculateWeekendDiscount(boolean isWeekend) {
        // 주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인
        return isWeekend ? 2023 : 0;
    }

    // 특별 할인
    public static double calculateSpecialDiscount(boolean hasStar) {
        // 이벤트 달력에 별이 있으면 총주문 금액에서 1,000원 할인
        return hasStar ? 1000 : 0;
    }

    public static double calculateDiscount(double totalPrice, LocalDate orderDate) {
        // 크리스마스 디데이 할인
        double christmasDiscount = calculateChristmasDiscount(orderDate.getDayOfMonth());

        // 주말/평일 여부 확인
        boolean isWeekday = orderDate.getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue() &&
                            orderDate.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue();
        boolean isWeekend = !isWeekday;

        // 평일/주말 할인
        double weekdayDiscount = calculateWeekdayDiscount(isWeekday);
        double weekendDiscount = calculateWeekendDiscount(isWeekend);

        // 특별 할인
        boolean hasSpecialDiscount = hasSpecialDiscount(orderDate);
        double specialDiscount = calculateSpecialDiscount(hasSpecialDiscount);

        return christmasDiscount + weekdayDiscount + weekendDiscount + specialDiscount;
    }
    
    public static boolean hasSpecialDiscount(LocalDate orderDate) {
        // 12월 3일, 10일, 17일, 24일, 25일, 31일에 별이 있는지 여부를 확인하는 로직
        int dayOfMonth = orderDate.getDayOfMonth();
        return (dayOfMonth == 3 || dayOfMonth == 10 || dayOfMonth == 17 || dayOfMonth == 24 ||
                dayOfMonth == 25 || dayOfMonth == 31);
    }

    public static double calculateFinalPrice(double totalPrice, double discount) {
        // 할인 후 예상 결제 금액 계산
        return totalPrice - discount;
    }

    public static Set<String> getValidMenuItems() {
        return menuPrices.keySet();
    }
}
