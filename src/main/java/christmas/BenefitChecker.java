package christmas;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class BenefitChecker {
    public static String checkBenefits(double totalPrice, LocalDate orderDate, Set<String> orderedItems) {
        StringBuilder benefits = new StringBuilder("");

        // 크리스마스 디데이 할인
        int dayOfMonth = orderDate.getDayOfMonth();
        double christmasDiscount = 0.0;

        if (dayOfMonth >= 1 && dayOfMonth <= 25) {
            christmasDiscount = DiscountCalculator.calculateChristmasDiscount(dayOfMonth);
        }        

        if (christmasDiscount > 0) {
            benefits.append("크리스마스 디데이 할인: -" + christmasDiscount + "원\n");
        }

         // 주말/평일 할인
        boolean isWeekday = orderDate.getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue() &&
                orderDate.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue();
        boolean isWeekend = !isWeekday;
        double weekdayDiscount = DiscountCalculator.calculateWeekdayDiscount(isWeekday, orderedItems);
        double weekendDiscount = DiscountCalculator.calculateWeekendDiscount(isWeekend, orderedItems);
        if (weekdayDiscount > 0) {
            benefits.append("평일 할인: -" + weekdayDiscount + "원\n");
        }
        if (weekendDiscount > 0) {
            benefits.append("주말 할인: -" + weekendDiscount + "원\n");
        }

        // 특별 할인
        boolean hasSpecialDiscount = DiscountCalculator.hasSpecialDiscount(orderDate);
        double specialDiscount = DiscountCalculator.calculateSpecialDiscount(hasSpecialDiscount);
        if (specialDiscount > 0) {
            benefits.append("\n특별 할인: -" + specialDiscount + "원\n");
        }

        // 증정 이벤트 확인
        String freeItem = FreeItemChecker.checkFreeItem(totalPrice);
        if ("샴페인".equals(freeItem)) {
            benefits.append("증정 이벤트: -25,000원\n");
        }

        if (benefits.length() == 0) {
            benefits.append("\n없음");
        }

        return benefits.toString();
    }
}
