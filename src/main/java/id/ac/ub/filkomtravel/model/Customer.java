package id.ac.ub.filkomtravel.model;

import id.ac.ub.filkomtravel.promotion.Promotion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Customer {
    private final String id;
    private final String fullName;
    private long balance;
    private final Map<String, CartItem> cart = new LinkedHashMap<>();
    private final List<Order> orderHistory = new ArrayList<>();
    private Promotion selectedPromotion;

    protected Customer(String id, String fullName, long initialBalance) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName must not be blank");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("initialBalance must not be negative");
        }
        this.id = id.trim();
        this.fullName = fullName.trim();
        this.balance = initialBalance;
    }

    public abstract boolean isEligibleForMemberPromotions(LocalDate referenceDate);

    public void topUp(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("top up amount must be positive");
        }
        balance = Math.addExact(balance, amount);
    }

    public void addToCart(Vehicle vehicle, int rentalDays, LocalDate startDate) {
        CartItem existing = cart.get(vehicle.getId());
        if (existing == null) {
            cart.put(vehicle.getId(), new CartItem(vehicle, rentalDays, startDate));
            return;
        }
        if (!existing.getStartDate().equals(startDate)) {
            throw new IllegalArgumentException("same vehicle cannot have two start dates in one cart");
        }
        existing.addDays(rentalDays);
    }

    public void removeFromCart(String vehicleId, int rentalDays) {
        CartItem item = cart.get(vehicleId);
        if (item == null) {
            throw new IllegalArgumentException("vehicle is not in cart: " + vehicleId);
        }
        if (rentalDays == item.getRentalDays()) {
            cart.remove(vehicleId);
        } else {
            item.removeDays(rentalDays);
        }
    }

    public long cartSubtotal() {
        return cart.values().stream().mapToLong(CartItem::subtotal).sum();
    }

    public Collection<CartItem> getCartItems() {
        return List.copyOf(cart.values());
    }

    public List<Order> getOrderHistory() {
        return List.copyOf(orderHistory);
    }

    public void selectPromotion(Promotion promotion) {
        this.selectedPromotion = promotion;
    }

    public Promotion getSelectedPromotion() {
        return selectedPromotion;
    }

    public void clearSelectedPromotion() {
        selectedPromotion = null;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public long getBalance() { return balance; }

    public void debit(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("debit amount must not be negative");
        }
        if (balance < amount) {
            throw new IllegalStateException("insufficient balance");
        }
        balance -= amount;
    }

    public void credit(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("credit amount must not be negative");
        }
        balance = Math.addExact(balance, amount);
    }

    public void recordOrder(Order order) {
        orderHistory.add(order);
        cart.clear();
        selectedPromotion = null;
    }
}
