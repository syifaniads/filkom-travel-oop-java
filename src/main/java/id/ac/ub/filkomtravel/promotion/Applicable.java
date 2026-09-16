package id.ac.ub.filkomtravel.promotion;

import id.ac.ub.filkomtravel.model.Customer;

import java.time.LocalDate;

public interface Applicable {
    boolean isCustomerEligible(Customer customer, LocalDate referenceDate);
    boolean isOrderEligible(OrderDraft order);
    PromotionResult calculate(OrderDraft order);
}
