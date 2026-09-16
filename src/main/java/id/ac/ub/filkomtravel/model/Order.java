package id.ac.ub.filkomtravel.model;

import id.ac.ub.filkomtravel.promotion.Promotion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public final class Order {
    private static final AtomicLong SEQUENCE = new AtomicLong(1);

    private final long orderNumber;
    private final Customer customer;
    private final List<CartItem> items;
    private final long subtotal;
    private final long discount;
    private final long cashback;
    private final long chargedAmount;
    private final Promotion promotion;
    private final LocalDateTime createdAt;
    private OrderStatus status;

    public Order(Customer customer, List<CartItem> items, Promotion promotion,
                 long discount, long cashback, long chargedAmount) {
        this.orderNumber = SEQUENCE.getAndIncrement();
        this.customer = Objects.requireNonNull(customer, "customer");
        this.items = List.copyOf(items);
        if (this.items.isEmpty()) {
            throw new IllegalArgumentException("order requires at least one item");
        }
        this.subtotal = this.items.stream().mapToLong(CartItem::subtotal).sum();
        this.promotion = promotion;
        this.discount = discount;
        this.cashback = cashback;
        this.chargedAmount = chargedAmount;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.SUCCESSFUL;
    }

    public long getOrderNumber() { return orderNumber; }
    public Customer getCustomer() { return customer; }
    public List<CartItem> getItems() { return items; }
    public long getSubtotal() { return subtotal; }
    public long getDiscount() { return discount; }
    public long getCashback() { return cashback; }
    public long getChargedAmount() { return chargedAmount; }
    public Promotion getPromotion() { return promotion; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public OrderStatus getStatus() { return status; }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }
}
