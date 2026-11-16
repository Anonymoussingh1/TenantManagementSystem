# Tenant Management System - Group Project Architecture & Room Integration Walkthrough

Successfully implemented **Lab 8 Group Project Architecture (Stages 1 through 5)** (`Lab 8 - App architecture, Git and App distribution.pdf`).

## Changes Implemented

### 1. Architectural Package Organization (Stage 1)
- Reorganized all Kotlin source files into clean architectural layers:
  - `ui.auth`: `LoginActivity.kt`, `RegisterActivity.kt`
  - `ui.main`: `MainActivity.kt`
  - `ui.dashboard`: `DashboardFragment.kt`
  - `ui.tenants`: `AddTenantFragment.kt`, `ViewTenantsFragment.kt`
  - `ui.viewmodel`: `TenantViewModel.kt`
  - `data.model`: `Tenant.kt` (Room Entity)
  - `data.local`: `TenantDao.kt`, `TenantDatabase.kt`
  - `data.repository`: `TenantRepository.kt`

### 2. ViewModel & LiveData (Stage 2)
- Implemented `TenantViewModel` extending `AndroidViewModel` to manage tenant operations and expose LiveData observables (`tenantCountText`, `totalRentText`, `allTenantsText`).

### 3. Room Database (Stage 3)
- Converted `Tenant` into a Room `@Entity` with a primary key.
- Created `TenantDao` with `@Insert` and `@Query` operations.
- Created `TenantDatabase` singleton instance with SQLite room builder.

### 4. UI & ViewModel Integration (Stage 4)
- Connected `DashboardFragment`, `AddTenantFragment`, and `ViewTenantsFragment` to use `activityViewModels<TenantViewModel>()` and observe LiveData changes or hand over save actions.

### 5. Data Layer Integration (Stage 5)
- Connected `TenantRepository` and `TenantViewModel` directly to the Room database (`TenantDatabase`), ensuring tenants persist locally across app restarts.

## Validation Results
- **Build**: Successfully compiled and built debug APK (`app:assembleDebug`) with 0 errors.
- **Tests**: Unit tests (`app:testDebugUnitTest`) passed successfully.
