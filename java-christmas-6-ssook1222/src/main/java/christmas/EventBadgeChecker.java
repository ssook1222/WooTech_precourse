package christmas;

public class EventBadgeChecker {
    public static String checkEventBadge(double totalBenefits) {
        String eventBadge = "";

        if (totalBenefits >= 20000) {
            eventBadge = "산타"; // 2만 원 이상 총혜택 시 산타 배지 부여
        } else if (totalBenefits >= 10000) {
            eventBadge = "트리"; // 1만 원 이상 총혜택 시 트리 배지 부여
        } else if (totalBenefits >= 5000) {
            eventBadge = "별"; // 5천 원 이상 총혜택 시 별 배지 부여
        }

        return eventBadge;
    }
}
