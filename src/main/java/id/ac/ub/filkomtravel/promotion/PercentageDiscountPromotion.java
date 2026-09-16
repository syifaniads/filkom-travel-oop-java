package id.ac.ub.filkomtravel.promotion;

import java.time.LocalDate;

public final class PercentageDiscountPromotion extends Promotion {
    public PercentageDiscountPromotion(String code, LocalDate startDate, LocalDate endDate,
                                       int percentage, long maximumDiscount, long minimumPurchase) {
        super(code, startDate, endDate, percentage, maximumDiscount, minimumPurchase);
    }

    @Override
    public PromotionResult calculate(OrderDraft order) {
        return new PromotionResult(cappedPercentage(order.subtotal()), 0);
    }
}
