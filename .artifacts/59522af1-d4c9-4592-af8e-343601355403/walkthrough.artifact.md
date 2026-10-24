# Tenant Management System - Navigation Practical 3 Walkthrough

Successfully implemented **Navigation Practical 3: Navigation, Safe Args and the Navigation Drawer** (`Lab 7_ Navigation Practical 3.pdf`).

## Changes Implemented

### 1. App Bar & NavHost (`activity_main.xml` & `MainActivity.kt`)
- Updated `activity_main.xml` to wrap the layout in `DrawerLayout`, containing a `MaterialToolbar` as the App Bar and a `FragmentContainerView` (`NavHostFragment`).
- Updated `MainActivity.kt` to initialize `NavController`, `AppBarConfiguration`, set up the Action Bar (`setSupportActionBar`), handle Up/Back navigation (`onSupportNavigateUp`), inflate the options menu (`main_menu.xml`), and handle drawer navigation (`navView.setupWithNavController`) and logout functionality.

### 2. Safe Args & Fragment Navigation (`nav_graph.xml`, `DashboardFragment.kt`, `AddTenantFragment.kt`, `ViewTenantsFragment.kt`)
- Added slide animations and back-stack cleanup (`popUpTo="@id/dashboardFragment"`) in `nav_graph.xml`.
- Updated `DashboardFragment` buttons to navigate using generated `DashboardFragmentDirections`.
- Updated `AddTenantFragment` save action to add the tenant to `TenantRepository` and pass the `Tenant` object safely via Safe Args (`AddTenantFragmentDirections.actionAddTenantFragmentToViewTenantsFragment(tenant)`).
- Updated `ViewTenantsFragment` to receive the `newTenant` argument using `navArgs()`, bind it via Data Binding (`binding.tenant`), and wire up the `CALL TENANT` button using an implicit intent (`ACTION_DIAL`).

### 3. Navigation Drawer & Options Menu (`drawer_menu.xml`, `main_menu.xml`, `nav_header.xml`)
- Created `drawer_menu.xml` and `main_menu.xml` with destination IDs matching the navigation graph.
- Created `nav_header.xml` header layout and bound the signed-in user's email from `LoginActivity` intent extras to `headerEmailTextView`.

## Validation Results
- **Build**: Successfully compiled and built debug APK (`app:assembleDebug`) with 0 errors.
- **Tests**: Unit tests (`app:testDebugUnitTest`) passed successfully.
