# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

EasyPOS is a Java SE 8 desktop Point-of-Sale application built with Swing, targeting Windows. It manages sales, inventory, suppliers, customers, and invoicing with multi-language support (English, Sinhala, Tamil) and offline-first operation via local SQLite with background sync to a remote REST API.

## Build System

This is a **NetBeans Ant project** (Java 1.8 source/target). Build via Apache Ant:

```
ant clean build   # Clean and compile
ant run           # Run the application (entry point: easyPOS.OpenScreen)
ant jar           # Package to dist/EasyPOS.jar
ant clean         # Remove build/ and dist/
```

To produce the Windows `.exe`, the Launch4j config at `build_config/EasyPOS_build_config.xml` wraps `dist/EasyPOS.jar`. There are no automated tests in this project.

## Architecture

### Layer Breakdown

```
UI (easyPOS.*)
    ↓
Control (control.*)          ← application state, event bus, permissions
    ↓
DB Operations (dbOperations.*)  ← SQL queries (SQLite + legacy MySQL)
    ↓
Databases: SQLite local  ←→  Remote MySQL via REST (webService.*)
```

### Key Singletons

- **`control.ApplicationDataManager`** — Central app state: current user, permissions, language, institute settings. Initialized at login, read everywhere.
- **`control.EventManager`** — Lightweight event bus (observer pattern). Used to decouple login events, menu navigation changes, and screen transitions between Swing panels.
- **`localDatabase.DatabaseManager`** — Manages the local SQLite connection (`myapp.db`). Creates all tables on first run.
- **`control.ServerDataSubmissionQueue`** — Async queue that syncs local changes to the server REST API when connectivity is available.

### Package Purposes

| Package | Purpose |
|---|---|
| `easyPOS` | Top-level Swing frames/panels: `OpenScreen` (main JFrame), login, sale, stock, supplier, customers, invoice, reports, settings, user, customerdisplay |
| `control` | Application logic, event bus, logging, runtime state |
| `dbOperations` | All SQL: `LoginDBOperation`, `SalesDBOperation`, `StockDBOperation`, `SuppliesDBOperation`, `SupplierDBOperation`, `ItemCategoryDBOperation`, `EventLogDBOperation`, `ItemMovement` |
| `appDataModels` | In-memory domain models (`UserAccountModel`, `ItemModel`, `CustomerModel`, etc.) |
| `serverDataModels` | Server-side DTOs for REST API serialization (`UserAccount`, `Item`, `SaleInvoice`, etc.) |
| `webService` | REST client using Jackson; posts to `https://moosero.lk/EASYPOS-API-PHP-master/api` |
| `tableModels` | `AbstractTableModel` subclasses for Swing `JTable` binding |
| `dataModels` | Enums: `MenuItemType`, `Language`, `MeasureUnit`, `MethodOfPayment`, `LogEvent` |
| `uiUtil` | Swing utility helpers |
| `util` | `DateTimeUtil` and similar |

### Dual Database Architecture

**Local SQLite** (`myapp.db`) is the primary store. `DatabaseManager` creates tables at startup:
- `user_account`, `user_permission`, `category`, `item`, `item_stock`, `measure_unit`, `institute`, `app_data`, `change_log`
- Tables have columns for all three languages: `*_sin`, `*_tam`, `*_eng`
- `change_log` tracks unsynced mutations (`is_synched` flag)

**Legacy MySQL** (`DBCons.java` → `jdbc:mysql://localhost:3306/pharmacy_management_system`) exists alongside SQLite. Some `dbOperations` classes still target MySQL; migration to SQLite is in progress. The `garbage/` package contains deprecated code being phased out.

**Server sync**: `ServerDataSubmissionQueue` reads unsynced `change_log` entries and posts them to the REST API via `WebService.java`. Offline operation is supported; changes queue until connectivity is restored.

### UI Pattern

- `OpenScreen` is the root JFrame. It hosts `HeaderPanel` and `MainMenuPanel`, swapping content panels for each module.
- Module screens (sale, stock, etc.) are `JPanel` subclasses instantiated on navigation events via `EventManager`.
- Look & Feel: JTattoo `AluminiumLookAndFeel`.
- Localization: `easyPOS/Bundle.properties` (English) and `Bundle_si_LK.properties` (Sinhala). `ApplicationDataManager` holds the active `Language` enum; UI components query it to select display strings.

## Dependencies

All JARs are bundled in `/lib` (SQLite, Jackson) and the project root (iText PDF, JasperReports, JFreeChart, JTattoo, MySQL connector, DateChooser). No Maven/Gradle — classpath is defined in `nbproject/project.properties`.
