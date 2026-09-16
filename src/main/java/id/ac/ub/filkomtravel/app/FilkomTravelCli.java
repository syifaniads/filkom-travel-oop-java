package id.ac.ub.filkomtravel.app;

import id.ac.ub.filkomtravel.model.Order;
import id.ac.ub.filkomtravel.model.Vehicle;
import id.ac.ub.filkomtravel.model.VehicleType;
import id.ac.ub.filkomtravel.promotion.CashbackPromotion;
import id.ac.ub.filkomtravel.promotion.PercentageDiscountPromotion;
import id.ac.ub.filkomtravel.service.TravelService;

import java.time.LocalDate;

public final class FilkomTravelCli {
    private FilkomTravelCli() {}

    public static void main(String[] args) {
        TravelService service = new TravelService();

        service.registerVehicle(new Vehicle("V001", "Toyota Avanza", "N 1234 AB", 350_000, VehicleType.CAR));
        service.registerVehicle(new Vehicle("V002", "Honda Vario", "N 5678 CD", 120_000, VehicleType.MOTORCYCLE));

        LocalDate today = LocalDate.now();
        service.registerMember("M001", "Demo Member", today.minusDays(90), 2_000_000);
        service.registerPromotion(new PercentageDiscountPromotion(
                "SAVE10", today.minusDays(1), today.plusDays(30), 10, 150_000, 300_000));
        service.registerPromotion(new CashbackPromotion(
                "CASH5", today.minusDays(1), today.plusDays(30), 5, 75_000, 200_000));

        service.addToCart("M001", "V001", 2, today.plusDays(3));
        service.selectPromotion("M001", "SAVE10");
        Order order = service.checkout("M001", today);

        System.out.printf("Order #%d | subtotal Rp%,d | discount Rp%,d | charged Rp%,d%n",
                order.getOrderNumber(), order.getSubtotal(), order.getDiscount(), order.getChargedAmount());
    }
}
