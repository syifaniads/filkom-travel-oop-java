package id.ac.ub.filkomtravel.promotion;

public record OrderDraft(long subtotal) {
    public OrderDraft {
        if (subtotal < 0) {
            throw new IllegalArgumentException("subtotal must not be negative");
        }
    }
}
