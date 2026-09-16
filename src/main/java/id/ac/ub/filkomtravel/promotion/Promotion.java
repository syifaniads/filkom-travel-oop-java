package id.ac.ub.filkomtravel.promotion;

import id.ac.ub.filkomtravel.model.Customer;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Promotion implements Applicable, Comparable<Promotion> {
    private final String code;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int percentage;
    private final long maximumBenefit;
    private final long minimumPurchase;

    protected Promotion(String code, LocalDate startDate, LocalDate endDate,
                        int percentage, long maximumBenefit, long minimumPurchase) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code must not be blank");
        }
        this.code = code.trim();
        this.startDate = Objects.requireNonNull(startDate, "startDate");
        this.endDate = Objects.requireNonNull(endDate, "endDate");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate must not be before startDate");
        }
        if (percentage <= 0 || percentage > 100) {
            throw new IllegalArgumentException("percentage must be between 1 and 100");
        }
        if (maximumBenefit < 0 || minimumPurchase < 0) {
            throw new IllegalArgumentException("promotion thresholds must not be negative");
        }
        this.percentage = percentage;
        this.maximumBenefit = maximumBenefit;
        this.minimumPurchase = minimumPurchase;
    }

    @Override
    public boolean isCustomerEligible(Customer customer, LocalDate referenceDate) {
        return customer.isEligibleForMemberPromotions(referenceDate)
                && !referenceDate.isBefore(startDate)
                && !referenceDate.isAfter(endDate);
    }

    @Override
    public boolean isOrderEligible(OrderDraft order) {
        return order.subtotal() >= minimumPurchase;
    }

    protected long cappedPercentage(long subtotal) {
        long raw = Math.multiplyExact(subtotal, percentage) / 100;
        return maximumBenefit == 0 ? raw : Math.min(raw, maximumBenefit);
    }

    public String getCode() { return code; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getPercentage() { return percentage; }
    public long getMaximumBenefit() { return maximumBenefit; }
    public long getMinimumPurchase() { return minimumPurchase; }

    @Override
    public int compareTo(Promotion other) {
        int byDate = this.startDate.compareTo(other.startDate);
        return byDate != 0 ? byDate : this.code.compareTo(other.code);
    }

    @Override
    public String toString() {
        return code;
    }
}
