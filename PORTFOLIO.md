# Portfolio Notes

## Short description

**Filkom Travel — Java OOP Coursework Refactor**  
Collaborative university project modeling vehicle rental, customer types, carts, orders, and promotion strategies using Java object-oriented programming; later refactored into a tested Maven project with CI and documented design trade-offs.

## CV-ready bullet

- Contributed to a Java OOP vehicle-rental coursework project using abstraction, inheritance, interfaces, polymorphism, collections, order/cart workflows, and discount/cashback promotion models; later reconstructed the project into a clean Maven architecture with JUnit tests and GitHub Actions CI.

## Interview talking points

- Why `Customer` is abstract and `Member`/`Guest` are concrete specializations.
- Why promotion calculation is polymorphic instead of a large `if/else` chain.
- Why shared `static` order history was a bug and per-customer ownership is safer.
- Why printing/reporting must not mutate balances.
- Why integer currency is safer than `double` for this coursework model.
- Why the historical hand-written binary search/merge sort were useful learning exercises but are not forced into the refactored application architecture.
- How you would add persistence: define a repository boundary, use transactions around checkout, and introduce vehicle availability constraints.
