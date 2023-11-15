package christmas;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DiscountCalculator {
    // 각 메뉴별 가격 정보
    private static final Map<String, Double> appetizerMenu = new HashMap<>();
    private static final Map<String, Double> mainMenu = new HashMap<>();
    private static final Map<String, Double> dessertMenu = new HashMap<>();
    private static final Map<String, Double> beverageMenu = new HashMap<>();

    static {
        // 에피타이저
        appetizerMenu.put("양송이수프", 6000.0);
        appetizerMenu.put("타파스", 5500.0);
        appetizerMenu.put("시저샐러드", 8000.0);

        // 메인
        mainMenu.put("티본스테이크", 55000.0);
        mainMenu.put("바비큐립", 54000.0);
        mainMenu.put("해산물파스타", 35000.0);
        mainMenu.put("크리스마스파스타", 25000.0);

        // 디저트
        dessertMenu.put("초코케이크", 15000.0);
        dessertMenu.put("아이스크림", 5000.0);

        // 음료
        beverageMenu.put("제로콜라", 3000.0);
        beverageMenu.put("레드와인", 60000.0);
        beverageMenu.put("샴페인", 25000.0);
    }

    public static double calculateTotalPrice(String[] orderedItems, int[] quantities) {
        double total = 0.0;
        for (int i = 0; i < orderedItems.length; i++) {
            String menuItem = orderedItems[i];
            int quantity = quantities[i];

            // 메뉴의 카테고리를 확인
            double menuPrice = getMenuPrice(menuItem);

            // 해당 카테고리의 메뉴 가격을 계산에 추가
            total += menuPrice * quantity;
        }
        return total;
    }

    // 메뉴의 카테고리를 확인하고, 해당 카테고리의 메뉴 가격을 반환
    private static double getMenuPrice(String menuItem) {
        if (appetizerMenu.containsKey(menuItem)) {
            return appetizerMenu.get(menuItem);
        } else if (mainMenu.containsKey(menuItem)) {
            return mainMenu.get(menuItem);
        } else if (dessertMenu.containsKey(menuItem)) {
            return dessertMenu.get(menuItem);
        } else if (beverageMenu.containsKey(menuItem)) {
            return beverageMenu.get(menuItem);
        } else {
            return 0.0;
        }
    }

    // 크리스마스 디데이 할인
    public static double calculateChristmasDiscount(int dayOfMonth) {
        // 12월 1일부터 25일까지의 크리스마스 디데이 할인
        if (dayOfMonth >= 1 && dayOfMonth <= 25) {
            int baseDiscount = 1000;
            int additionalDiscount = 100 * (dayOfMonth - 1);
            return baseDiscount + additionalDiscount;
        } else {
            // 25일 이후에는 할인 없음
            return 0.0;
        }
    }

    public static double calculateWeekdayDiscount(boolean isWeekday, Set<String> orderedItems) {
        // 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인
        if (isWeekday) {
            double weekdayDiscount = 0.0;
            for (String menuItem : orderedItems) {
                if (getDessertMenuItems().contains(menuItem)) {
                    weekdayDiscount += 2023;
                }
            }
            return weekdayDiscount;
        }
        return 0;
    }

    public static double calculateWeekendDiscount(boolean isWeekend, Set<String> orderedItems) {
        // 주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인
        if (isWeekend) {
            double weekendDiscount = 0.0;
            for (String menuItem : orderedItems) {
                if (getMainMenuItems().contains(menuItem)) {
                    weekendDiscount += 2023;
                }
            }
            return weekendDiscount;
        }
        return 0;
    }

    // 특별 할인
    public static double calculateSpecialDiscount(boolean hasStar) {
        // 이벤트 달력에 별이 있으면 총주문 금액에서 1,000원 할인
        return hasStar ? 1000 : 0;
    }

    public static double calculateDiscount(double totalPrice, LocalDate orderDate, Set<String> orderedItems) {
        // 크리스마스 디데이 할인
        double christmasDiscount = calculateChristmasDiscount(orderDate.getDayOfMonth());

        // 주말/평일 여부 확인
        boolean isWeekday = orderDate.getDayOfWeek().getValue() >= DayOfWeek.SUNDAY.getValue() &&
                orderDate.getDayOfWeek().getValue() <= DayOfWeek.THURSDAY.getValue();
        boolean isWeekend = !isWeekday;

        // 평일/주말 할인
        double weekdayDiscount = calculateWeekdayDiscount(isWeekday, orderedItems);
        double weekendDiscount = calculateWeekendDiscount(isWeekend, orderedItems);

        // 특별 할인
        boolean hasSpecialDiscount = hasSpecialDiscount(orderDate);
        double specialDiscount = calculateSpecialDiscount(hasSpecialDiscount);

        // 증정 할인
        double giveDiscount = 0.0;
        String freeItem = FreeItemChecker.checkFreeItem(totalPrice);

        if ("샴페인".equals(freeItem)) {
            giveDiscount += 25000; 
        }

        return christmasDiscount + weekdayDiscount + weekendDiscount + specialDiscount + giveDiscount;
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

    public static Set<String> getAppetizerMenuItems() {
        return appetizerMenu.keySet();
    }

    public static Set<String> getMainMenuItems() {
        return mainMenu.keySet();
    }

    public static Set<String> getDessertMenuItems() {
        return dessertMenu.keySet();
    }

    public static Set<String> getBeverageMenuItems() {
        return beverageMenu.keySet();
    }
}
