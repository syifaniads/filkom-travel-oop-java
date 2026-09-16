package id.ac.ub.filkomtravel.model;

import java.time.LocalDate;
import java.util.Objects;

public final class CartItem {
    private final Vehicle vehicle;
    private int rentalDays;
    private final LocalDate startDate;

    public CartItem(Vehicle vehicle, int rentalDays, LocalDate startDate) {
        this.vehicle = Objects.requireNonNull(vehicle, "vehicle");
        this.startDate = Objects.requireNonNull(startDate, "startDate");
        if (rentalDays <= 0) {
            throw new IllegalArgumentException("rentalDays must be positive");
        }
        this.rentalDays = rentalDays;
    }

    public void addDays(int additionalDays) {
        if (additionalDays <= 0) {
            throw new IllegalArgumentException("additionalDays must be positive");
        }
        this.rentalDays += additionalDays;
    }

    public void removeDays(int days) {
        if (days <= 0 || days > rentalDays) {
            throw new IllegalArgumentException("days must be between 1 and current rental days");
        }
        this.rentalDays -= days;
    }

    public long subtotal() {
        return Math.multiplyExact(vehicle.getDailyRate(), rentalDays);
    }

    public Vehicle getVehicle() { return vehicle; }
    public int getRentalDays() { return rentalDays; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return startDate.plusDays(rentalDays); }
}
