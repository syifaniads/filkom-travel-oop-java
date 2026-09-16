package id.ac.ub.filkomtravel.service;

import id.ac.ub.filkomtravel.model.Guest;
import id.ac.ub.filkomtravel.model.Member;
import id.ac.ub.filkomtravel.model.Order;
import id.ac.ub.filkomtravel.model.Vehicle;
import id.ac.ub.filkomtravel.model.VehicleType;
import id.ac.ub.filkomtravel.promotion.CashbackPromotion;
import id.ac.ub.filkomtravel.promotion.PercentageDiscountPromotion;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TravelServiceTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 9, 16);

    @Test
    void memberCanCheckoutWithPercentageDiscount() {
        TravelService service = new TravelService();
        service.registerVehicle(new Vehicle("C01", "Avanza", "N 1111 AA", 300_000, VehicleType.CAR));
        Member member = service.registerMember("M01", "Member", TODAY.minusDays(60), 1_000_000);
        service.registerPromotion(new PercentageDiscountPromotion(
                "SAVE10", TODAY.minusDays(1), TODAY.plusDays(1), 10, 100_000, 250_000));

        service.addToCart("M01", "C01", 2, TODAY.plusDays(2));
        service.selectPromotion("M01", "SAVE10");
        Order order = service.checkout("M01", TODAY);

        assertEquals(600_000, order.getSubtotal());
        assertEquals(60_000, order.getDiscount());
        assertEquals(540_000, order.getChargedAmount());
        assertEquals(460_000, member.getBalance());
        assertTrue(member.getCartItems().isEmpty());
        assertEquals(1, member.getOrderHistory().size());
    }

    @Test
    void cashbackIsCreditedAfterCharge() {
        TravelService service = new TravelService();
        service.registerVehicle(new Vehicle("MTR1", "Vario", "N 2222 BB", 100_000, VehicleType.MOTORCYCLE));
        Member member = service.registerMember("M02", "Member", TODAY.minusDays(90), 500_000);
        service.registerPromotion(new CashbackPromotion(
                "CASH10", TODAY.minusDays(1), TODAY.plusDays(1), 10, 50_000, 100_000));

        service.addToCart("M02", "MTR1", 2, TODAY.plusDays(1));
        service.selectPromotion("M02", "CASH10");
        Order order = service.checkout("M02", TODAY);

        assertEquals(200_000, order.getSubtotal());
        assertEquals(20_000, order.getCashback());
        assertEquals(200_000, order.getChargedAmount());
        assertEquals(320_000, member.getBalance());
    }

    @Test
    void guestCannotUseMemberPromotion() {
        TravelService service = new TravelService();
        service.registerVehicle(new Vehicle("C02", "Brio", "N 3333 CC", 250_000, VehicleType.CAR));
        Guest guest = service.registerGuest("G01", 500_000);
        service.registerPromotion(new PercentageDiscountPromotion(
                "SAVE5", TODAY.minusDays(1), TODAY.plusDays(1), 5, 50_000, 100_000));

        service.addToCart("G01", "C02", 1, TODAY.plusDays(1));
        service.selectPromotion("G01", "SAVE5");

        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> service.checkout("G01", TODAY));
        assertTrue(error.getMessage().contains("not eligible"));
        assertEquals(500_000, guest.getBalance());
    }

    @Test
    void duplicateLicensePlateIsRejected() {
        TravelService service = new TravelService();
        service.registerVehicle(new Vehicle("C01", "Avanza", "N 4444 DD", 300_000, VehicleType.CAR));

        assertThrows(IllegalArgumentException.class, () ->
                service.registerVehicle(new Vehicle("C02", "Xenia", "n 4444 dd", 280_000, VehicleType.CAR)));
    }

    @Test
    void insufficientBalanceDoesNotCreateOrder() {
        TravelService service = new TravelService();
        service.registerVehicle(new Vehicle("C03", "Innova", "N 5555 EE", 500_000, VehicleType.CAR));
        Member member = service.registerMember("M03", "Member", TODAY.minusDays(90), 100_000);
        service.addToCart("M03", "C03", 1, TODAY.plusDays(1));

        assertThrows(IllegalStateException.class, () -> service.checkout("M03", TODAY));
        assertEquals(0, member.getOrderHistory().size());
        assertFalse(member.getCartItems().isEmpty());
    }
}
