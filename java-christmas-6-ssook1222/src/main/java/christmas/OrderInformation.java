package christmas;

public class OrderInformation {
    public static void displayOrderInfo(String[] orderedItems, int[] quantities) {
        System.out.println("<주문 메뉴>");
        for (int i = 0; i < orderedItems.length; i++) {
            System.out.println(orderedItems[i] + " " + quantities[i] + "개");
        }
        System.out.println("");
    }
}
