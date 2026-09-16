# Engineering Review

This document records the main technical problems found in the historical coursework and how the portfolio reconstruction addresses them.

## Repository hygiene

Historical state included duplicate Java source trees, committed `.class` files, a Maven `target/` directory, and multiple copies of the same classes. The portfolio branch keeps only source, tests, build metadata, and documentation. Generated artifacts are ignored.

## Privacy and portability

The GUI prototype exposed student identifiers and contained hard-coded Windows paths for video/images. The portfolio branch removes those identifiers and does not depend on local filesystem assets.

## Encapsulation

Historical classes exposed many package-visible fields (`IDMenu`, `Harga`, `subTotal`, `promoCode`, etc.). The refactor makes state private and provides explicit accessors/behavior methods. This reduces accidental cross-class mutation.

## Order history ownership

Historical `Customer` declared `orderHistory` as `static`, which makes order history shared by every customer. The refactor makes order history an instance-level collection so each customer owns only their own orders.

## Payment side effects

Historical printing logic could mutate balance, meaning displaying an order was capable of changing business state. Checkout is now the only path that debits/credits balance; getters/reporting are side-effect free.

## Equality and loops

Historical code contained correctness risks such as `if (order == order)`, string comparisons using `==`, and a malformed index loop in `getLastIndex`. The refactor removes those paths and covers checkout behavior with tests.

## Money representation

Historical code mixed `int` balances and `double` subtotal/promotion calculations. The reconstruction uses `long` integer Rupiah values. This avoids floating-point rounding errors for the integer-currency model used by the coursework.

## Promotion polymorphism

The original project already had a good OOP idea: an abstract `Promotion` with concrete discount/cashback subclasses. The refactor keeps that idea but moves eligibility and calculation behind a consistent interface and returns a typed `PromotionResult`.

## Service boundary

The original `MainTravel` combined command parsing, repositories/collections, searching/sorting, validation, cart mutation, checkout, and printing. `TravelService` now acts as a clear application boundary while entities own domain invariants.

## Tests and CI

The original repository had no automated verification. The portfolio branch adds JUnit 5 tests for core business rules and a GitHub Actions workflow running `mvn verify`.
