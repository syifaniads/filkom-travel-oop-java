# Limitations

This repository demonstrates OOP design and a cleaned academic domain model. It does not claim to be a production travel/rental platform.

Known scope boundaries:

- in-memory state only; restarting loses all data;
- no concurrent reservation locking or availability calendar;
- no relational database or transaction boundary;
- no HTTP API, authentication, authorization, or payment gateway;
- no persisted audit log;
- the CLI is a demonstration entry point, not a complete user interface;
- the 2026 code is a portfolio refactor, not the exact code submitted in 2024.

The historical GUI remains in the archive branch because its local asset paths make it non-reproducible without the original machine files.
