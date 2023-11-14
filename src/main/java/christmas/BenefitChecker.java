package christmas;

import java.time.LocalDate;

public class BenefitChecker {
    public static String checkBenefits(double totalPrice, LocalDate orderDate) {
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
        boolean isWeekday = orderDate.getDayOfWeek().getValue() >= 1 && orderDate.getDayOfWeek().getValue() <= 5;
        boolean isWeekend = !isWeekday;
        double weekdayDiscount = DiscountCalculator.calculateWeekdayDiscount(isWeekday);
        double weekendDiscount = DiscountCalculator.calculateWeekendDiscount(isWeekend);
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

        // 프로젝트에 따라 다양한 혜택을 추가하거나 조건을 변경

        if (benefits.length() == 0) {
            benefits.append("\n없음");
        }

        return benefits.toString();
    }
}
