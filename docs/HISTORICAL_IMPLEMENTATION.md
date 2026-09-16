# Historical Implementation Map

The archived 2024 repository contains several overlapping copies of the same project. The most complete source set was found under `PBO3-FINAL GUI/PBO3-main/Kode/`, while a separate `NETBEANS GUI FILKOM TRAVEL/AplikasiFilkomTravel/` tree contains an experimental Swing/JavaFX interface.

## Original concepts retained

| Historical artifact | Portfolio equivalent | Notes |
|---|---|---|
| `Customer` abstract class | `model/Customer` | Still the base abstraction, but state is encapsulated per customer. |
| `Member extends Customer` | `model/Member` | Membership age remains part of promotion eligibility. |
| `Guest extends Customer` | `model/Guest` | Guests remain ineligible for member promotions. |
| `Menu` | `model/Vehicle` | Renamed to reflect the actual vehicle-rental domain. |
| `CartItem` | `model/CartItem` | Rental duration and start date are explicit typed values. |
| `Order` | `model/Order` | Immutable checkout snapshot with totals/status. |
| `Applicable` | `promotion/Applicable` | Retained as a promotion behavior contract. |
| `Promotion` | `promotion/Promotion` | Retained as an abstract base class. |
| `PercentOffPromo` | `PercentageDiscountPromotion` | Same percentage/cap idea with clearer naming. |
| `CashbackPromo` | `CashbackPromotion` | Same percentage/cap idea with clearer naming. |
| `MainTravel` | `service/TravelService` + demo app | Parsing/orchestration separated from domain rules. |

## Historical algorithm exercises

The original `MainTravel` included hand-written binary search for menu lookup and merge sort for promotions. Those are useful coursework artifacts, but the portfolio refactor does not keep them in the runtime path simply to appear more complex. Java collections/maps and deterministic sorting are used where appropriate.

This is an intentional distinction between **demonstrating an algorithm in coursework** and **choosing maintainable primitives in an application design**.

## GUI prototype

The historical GUI prototype used Swing plus JavaFX media components. It also referenced absolute paths such as a personal Windows `Downloads` directory and a local video path. It is retained only in the archive branch as provenance; it is not presented as a reproducible UI in the portfolio branch.
