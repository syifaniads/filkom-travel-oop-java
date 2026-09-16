package id.ac.ub.filkomtravel.model;

import java.util.Objects;

public final class Vehicle implements Comparable<Vehicle> {
    private final String id;
    private final String name;
    private final String licensePlate;
    private final long dailyRate;
    private final VehicleType type;

    public Vehicle(String id, String name, String licensePlate, long dailyRate, VehicleType type) {
        this.id = requireText(id, "id");
        this.name = requireText(name, "name");
        this.licensePlate = requireText(licensePlate, "licensePlate");
        if (dailyRate <= 0) {
            throw new IllegalArgumentException("dailyRate must be positive");
        }
        this.dailyRate = dailyRate;
        this.type = Objects.requireNonNull(type, "type");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLicensePlate() { return licensePlate; }
    public long getDailyRate() { return dailyRate; }
    public VehicleType getType() { return type; }

    @Override
    public int compareTo(Vehicle other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return "%s (%s) - %s".formatted(name, licensePlate, type);
    }
}
