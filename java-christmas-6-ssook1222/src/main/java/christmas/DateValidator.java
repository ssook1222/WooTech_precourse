package christmas;

import java.util.*;

public class DateValidator {
    public static boolean validateDate(int date) {
        if (date >= 1 && date <= 31) {
            return true; // 유효한 날짜
        } else {
            System.out.println("[ERROR] 입력된 날짜가 유효하지 않습니다. 1 이상 31 이하의 숫자를 입력해주세요.");
            return false; // 유효하지 않은 날짜
        }
    }

    public static boolean validateMenu(String[] orderedItems) {
        // 중복을 확인하기 위해 Set을 사용
        Set<String> uniqueItems = new HashSet<>();
    
        // 메뉴와 수량을 확인하는 정규표현식
        String menuFormatRegex = "[\\p{L}\\s]+-\\d+";
        
        // Set<String> validMenuItems = DiscountCalculator.getValidMenuItems();
    
        for (String item : orderedItems) {
            if (!item.matches(menuFormatRegex)) {
                System.out.println("[ERROR] 주문한 메뉴의 형식이 올바르지 않습니다. (예: 메뉴-수량)");
                return false; // 형식에 맞지 않는 경우
            }

            String menuItemName = getMenuItemName(item);
            Set<String> validAppetizerItems = DiscountCalculator.getAppetizerMenuItems();
            Set<String> validMainMenuItems = DiscountCalculator.getMainMenuItems();
            Set<String> validDessertMenuItems = DiscountCalculator.getDessertMenuItems();
            Set<String> validBeverageMenuItems = DiscountCalculator.getBeverageMenuItems();

            if (!validAppetizerItems.contains(menuItemName) &&
                !validMainMenuItems.contains(menuItemName) &&
                !validDessertMenuItems.contains(menuItemName) &&
                !validBeverageMenuItems.contains(menuItemName)) {
                System.out.println("[ERROR] 주문한 메뉴가 유효하지 않습니다.");
                return false; // 메뉴판에 없는 메뉴를 주문해서 유효하지 않은 경우
            }
            if (!uniqueItems.add(item)) {
                System.out.println("[ERROR] 중복된 메뉴가 있습니다.");
                return false; // 중복된 경우
            }
        }
        return true; // 모든 메뉴가 유효하고 중복이 없는 경우
    }
    
    private static String getMenuItemName(String menuItem) {
        // 메뉴-수량 형식에서 메뉴 이름만 추출
        return menuItem.split("-")[0];
    }

}
