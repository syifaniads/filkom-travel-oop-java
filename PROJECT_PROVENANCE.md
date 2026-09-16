# Project Provenance

## Historical project

- Context: Object-Oriented Programming university coursework.
- Original development period visible in repository history: May–June 2024, with later file uploads in December 2024.
- Historical domain: Filkom Travel vehicle-rental application.
- Original source comment identified a four-person team:
  - Latifa Anggia Fitriana
  - Maulia Dwi Anthesa Sugeha
  - Syifani Adillah Salsabila
  - Kusuma Anisa Anggrani

Student identifiers are deliberately omitted from this branch.

## Contribution evidence

The GitHub history is useful evidence but is not treated as proof that every line in the repository was authored by one person.

Direct repository evidence includes:

- `dc5db7d710e1c026d51df9896431e5f0ae8b35d3` — authored through the `syifaniads` account; a major June 2024 source revision introducing/revising the core OOP source set.
- `df0097712fb010eee41ccf872e7b853c7991b5ad` — creation of `MainFilkomTravel.java`.
- multiple May–June 2024 updates to `Customer.java`, `MainTravel.java`, `Guest.java`, `Order.java`, `Promotion.java`, and related classes.
- December 2024 uploads by the same GitHub account containing duplicated/final project folders.

Because the coursework was collaborative, the portfolio description uses **contributed to / developed as part of a team** wording rather than sole-authorship wording.

## 2026 portfolio reconstruction

The `main` branch is not a byte-for-byte representation of the 2024 submission. It is a deliberate portfolio refactor that:

- preserves the core rental, customer, cart, order, and promotion domain;
- keeps the original OOP themes of abstraction, inheritance, interfaces, and polymorphism;
- removes committed build outputs and duplicate source trees;
- removes student IDs and machine-specific filesystem paths;
- reorganizes the code into a Maven package layout;
- fixes obvious correctness/design problems found during review;
- adds unit tests and CI;
- documents historical-versus-new behavior.

The untouched state before this cleanup remains available in `archive/original-coursework-2024` for provenance.
