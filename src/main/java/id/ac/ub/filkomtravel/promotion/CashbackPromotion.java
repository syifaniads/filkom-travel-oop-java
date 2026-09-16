package id.ac.ub.filkomtravel.promotion;

import java.time.LocalDate;

public final class CashbackPromotion extends Promotion {
    public CashbackPromotion(String code, LocalDate startDate, LocalDate endDate,
                             int percentage, long maximumCashback, long minimumPurchase) {
        super(code, startDate, endDate, percentage, maximumCashback, minimumPurchase);
    }

    @Override
    public PromotionResult calculate(OrderDraft order) {
        return new PromotionResult(0, cappedPercentage(order.subtotal()));
    }
}
