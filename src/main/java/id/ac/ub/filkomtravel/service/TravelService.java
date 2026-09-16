package id.ac.ub.filkomtravel.service;

import id.ac.ub.filkomtravel.model.CartItem;
import id.ac.ub.filkomtravel.model.Customer;
import id.ac.ub.filkomtravel.model.Guest;
import id.ac.ub.filkomtravel.model.Member;
import id.ac.ub.filkomtravel.model.Order;
import id.ac.ub.filkomtravel.model.Vehicle;
import id.ac.ub.filkomtravel.promotion.OrderDraft;
import id.ac.ub.filkomtravel.promotion.Promotion;
import id.ac.ub.filkomtravel.promotion.PromotionResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TravelService {
    private final Map<String, Customer> customers = new LinkedHashMap<>();
    private final Map<String, Vehicle> vehicles = new LinkedHashMap<>();
    private final Map<String, Promotion> promotions = new LinkedHashMap<>();

    public Member registerMember(String id, String fullName, LocalDate membershipDate, long initialBalance) {
        ensureUniqueCustomer(id);
        Member member = new Member(id, fullName, membershipDate, initialBalance);
        customers.put(id, member);
        return member;
    }

    public Guest registerGuest(String id, long initialBalance) {
        ensureUniqueCustomer(id);
        Guest guest = new Guest(id, initialBalance);
        customers.put(id, guest);
        return guest;
    }

    public void registerVehicle(Vehicle vehicle) {
        if (vehicles.containsKey(vehicle.getId())) {
            throw new IllegalArgumentException("vehicle id already exists: " + vehicle.getId());
        }
        boolean duplicatePlate = vehicles.values().stream()
                .anyMatch(v -> v.getLicensePlate().equalsIgnoreCase(vehicle.getLicensePlate()));
        if (duplicatePlate) {
            throw new IllegalArgumentException("license plate already exists: " + vehicle.getLicensePlate());
        }
        vehicles.put(vehicle.getId(), vehicle);
    }

    public void registerPromotion(Promotion promotion) {
        if (promotions.putIfAbsent(promotion.getCode(), promotion) != null) {
            throw new IllegalArgumentException("promotion already exists: " + promotion.getCode());
        }
    }

    public void topUp(String customerId, long amount) {
        customer(customerId).topUp(amount);
    }

    public void addToCart(String customerId, String vehicleId, int rentalDays, LocalDate startDate) {
        customer(customerId).addToCart(vehicle(vehicleId), rentalDays, startDate);
    }

    public void removeFromCart(String customerId, String vehicleId, int rentalDays) {
        customer(customerId).removeFromCart(vehicleId, rentalDays);
    }

    public void selectPromotion(String customerId, String promotionCode) {
        Customer customer = customer(customerId);
        Promotion promotion = promotions.get(promotionCode);
        if (promotion == null) {
            throw new IllegalArgumentException("unknown promotion: " + promotionCode);
        }
        customer.selectPromotion(promotion);
    }

    public Order checkout(String customerId, LocalDate checkoutDate) {
        Customer customer = customer(customerId);
        List<CartItem> items = new ArrayList<>(customer.getCartItems());
        if (items.isEmpty()) {
            throw new IllegalStateException("cart is empty");
        }

        long subtotal = customer.cartSubtotal();
        OrderDraft draft = new OrderDraft(subtotal);
        Promotion promotion = customer.getSelectedPromotion();
        PromotionResult result = PromotionResult.none();

        if (promotion != null) {
            if (!promotion.isCustomerEligible(customer, checkoutDate)) {
                throw new IllegalStateException("customer is not eligible for promotion " + promotion.getCode());
            }
            if (!promotion.isOrderEligible(draft)) {
                throw new IllegalStateException("order does not meet promotion minimum purchase");
            }
            result = promotion.calculate(draft);
        }

        long charge = subtotal - result.discount();
        customer.debit(charge);
        customer.credit(result.cashback());

        Order order = new Order(customer, items, promotion, result.discount(), result.cashback(), charge);
        customer.recordOrder(order);
        return order;
    }

    public Customer customer(String id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new IllegalArgumentException("unknown customer: " + id);
        }
        return customer;
    }

    public Vehicle vehicle(String id) {
        Vehicle vehicle = vehicles.get(id);
        if (vehicle == null) {
            throw new IllegalArgumentException("unknown vehicle: " + id);
        }
        return vehicle;
    }

    public Collection<Vehicle> vehicles() {
        return List.copyOf(vehicles.values());
    }

    public Collection<Promotion> promotions() {
        return promotions.values().stream().sorted().toList();
    }

    private void ensureUniqueCustomer(String id) {
        if (customers.containsKey(id)) {
            throw new IllegalArgumentException("customer already exists: " + id);
        }
    }
}
