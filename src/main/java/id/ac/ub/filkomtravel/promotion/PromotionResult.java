package id.ac.ub.filkomtravel.promotion;

public record PromotionResult(long discount, long cashback) {
    public PromotionResult {
        if (discount < 0 || cashback < 0) {
            throw new IllegalArgumentException("promotion values must not be negative");
        }
    }

    public static PromotionResult none() {
        return new PromotionResult(0, 0);
    }
}
