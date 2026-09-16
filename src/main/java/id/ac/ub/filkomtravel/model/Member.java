package id.ac.ub.filkomtravel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class Member extends Customer {
    private final LocalDate membershipDate;

    public Member(String id, String fullName, LocalDate membershipDate, long initialBalance) {
        super(id, fullName, initialBalance);
        this.membershipDate = Objects.requireNonNull(membershipDate, "membershipDate");
    }

    @Override
    public boolean isEligibleForMemberPromotions(LocalDate referenceDate) {
        return ChronoUnit.DAYS.between(membershipDate, referenceDate) > 30;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }
}
