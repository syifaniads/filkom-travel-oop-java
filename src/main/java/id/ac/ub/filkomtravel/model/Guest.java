package id.ac.ub.filkomtravel.model;

import java.time.LocalDate;

public final class Guest extends Customer {
    public Guest(String id, long initialBalance) {
        super(id, "Guest", initialBalance);
    }

    @Override
    public boolean isEligibleForMemberPromotions(LocalDate referenceDate) {
        return false;
    }
}
