# Source Evidence

This portfolio branch is grounded in the repository's historical 2024 coursework rather than being presented as a brand-new project.

## Historical source locations

The pre-refactor repository contained the same project in several overlapping locations. The most useful retained evidence is preserved on `archive/original-coursework-2024`:

- root-level Java classes such as `Customer.java`, `Member.java`, `Guest.java`, `Promotion.java`, and `MainTravel.java`;
- `PBO3-main/` — a copied coursework source set with compiled `.class` files;
- `PBO3-FINAL GUI/PBO3-main/Kode/` — a later source snapshot containing the customer/order/promotion implementation;
- `NETBEANS GUI FILKOM TRAVEL/AplikasiFilkomTravel/` — a Maven/NetBeans Swing + JavaFX GUI prototype.

## OOP evidence from the historical source

The historical code directly supports the following claims:

- `Customer` was an abstract base class.
- `Member` and `Guest` extended `Customer`.
- `Applicable` defined a promotion contract.
- `Promotion` was abstract and implemented `Applicable` and `Comparable<Promotion>`.
- `PercentOffPromo` and `CashbackPromo` extended `Promotion`.
- carts were composed of `CartItem` objects referencing menu/vehicle objects.
- orders stored rental items, customer, subtotal, status, and promotion state.
- `MainTravel` implemented command-driven workflows including customer creation, vehicle/menu creation, promotion creation, cart operations, top-up, checkout, and order history.
- the historical command implementation also contained hand-written binary search and merge sort exercises.

## Direct repository history

Notable commits visible in the repository history include:

- `dc5db7d710e1c026d51df9896431e5f0ae8b35d3` — `New PBO-3 Main`; authored through the `syifaniads` GitHub account and introduced/revised a broad core source set.
- `df0097712fb010eee41ccf872e7b853c7991b5ad` — creation of `MainFilkomTravel.java`.
- multiple May–June 2024 commits updating `Customer`, `MainTravel`, `Guest`, `Order`, `Status`, `Promotion`, and related code.
- December 2024 uploads containing the later duplicated/final folders.

These commits demonstrate hands-on contribution from the portfolio owner, but because the source itself lists a four-person team, they are not used to claim sole authorship of the original coursework.

## Portfolio-only work

The following are 2026 portfolio additions/reconstructions rather than claims about the original submission:

- standard Maven source layout and package structure;
- `TravelService` application boundary;
- encapsulated domain state and validation;
- integer Rupiah money model;
- correctness fixes documented in `docs/ENGINEERING_REVIEW.md`;
- JUnit 5 test suite;
- GitHub Actions Java CI;
- architecture, provenance, limitations, and portfolio documentation.
