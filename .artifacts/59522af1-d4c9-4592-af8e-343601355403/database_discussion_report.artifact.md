# Group Discussion Report: Android Local Data Persistence & Room Database

---

## 1. Database Selection: Types and Decision Factors

When designing an application, choosing the right database is one of the most critical architectural decisions. Databases are broadly categorized based on how they store, structure, and access data.

### Types of Databases Available in Mobile Development:
1. **Relational Databases (SQL - e.g., SQLite, Room, MySQL, PostgreSQL):**
   - **Structure:** Data is stored in tables with rows and columns. Tables relate to one another using foreign keys (e.g., a `Tenant` table linked to a `Lease` table).
   - **Best For:** Complex queries, structured data, strict data integrity (ACID compliance), and relationships.
2. **Non-Relational Databases (NoSQL - e.g., Firebase Firestore, MongoDB, Realm):**
   - **Structure:** Data is stored as documents (JSON-like objects), key-value pairs, or graphs rather than fixed tables.
   - **Best For:** Unstructured/semi-structured data, real-time synchronization, rapid schema changes, and hierarchical data.
3. **Local vs. Cloud Databases:**
   - **Local (e.g., SQLite/Room):** Stored directly on the device storage. Essential for offline-first functionality, fast local access, and privacy.
   - **Cloud/Remote (e.g., Firebase, Supabase, AWS):** Stored on remote servers. Essential for multi-device sync, cloud backups, and user authentication.

### Factors to Consider When Choosing a Database:
- **Data Structure & Complexity:** Does the app require complex relational queries and joins? If yes, choose SQL. If data is flexible and hierarchical, NoSQL.
- **Offline Capabilities:** Does the app need to work without an internet connection? Local databases (like Room) are mandatory.
- **Scalability & Performance:** How much data will be stored locally versus synced to the cloud?
- **Ease of Integration & Maintenance:** How steep is the learning curve? (e.g., Room provides compile-time safety over raw SQLite).
- **Security & Compliance:** Where is sensitive user data stored and encrypted?

---

## 2. Entities: Structuring and Representing Data

An **Entity** represents a table within a relational database. In Android's Room persistence library, an Entity is a annotated Kotlin data class that defines how data is structured and mapped to table rows.

### Key Concepts & Annotations:
- **`@Entity`**: Marks a class as a database table. By default, the class name becomes the table name (or a custom name can be specified via `tableName = "tenants"`).
- **`@PrimaryKey`**: Uniquely identifies each row in the table. Often paired with `autoGenerate = true` for auto-incrementing IDs.
- **Columns & Properties**: Every property in the data class corresponds to a column in the table. Room supports primitive types, Strings, and complex converters (TypeConverters) for custom objects.

### Example in Kotlin:
```kotlin
@Entity(tableName = "tenants")
data class Tenant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val phone: String,
    val rent: Double
)
```
- **Why it matters for discussion:** Entities bridge the gap between object-oriented programming (Kotlin objects) and relational data storage (SQL tables).

---

## 3. DAO (Data Access Object): Accessing and Managing Data

The **Data Access Object (DAO)** is the primary component of Room that is responsible for defining database interactions. Instead of writing boilerplate JDBC code or raw SQLite queries scattered across the app, DAOs use clean Kotlin interfaces or abstract classes with SQL annotations.

### Key Concepts & Annotations:
- **`@Dao`**: Tells Room that this interface/class handles database operations.
- **Query Annotations**:
  - **`@Insert`**: Automatically generates SQL to insert objects into the table.
  - **`@Update`**: Updates existing records.
  - **`@Delete`**: Removes records.
  - **`@Query("SELECT * FROM tenants")`**: Executes custom SQL queries with compile-time verification (Room checks your SQL syntax before you run the app!).

### Example in Kotlin:
```kotlin
@Dao
interface TenantDao {
    @Insert
    suspend fun insertTenant(tenant: Tenant)

    @Query("SELECT * FROM tenants")
    fun getAllTenants(): Flow<List<Tenant>> // Reactive stream of data

    @Delete
    suspend fun deleteTenant(tenant: Tenant)
}
```
- **Why it matters for discussion:** DAOs decouple database logic from UI controllers (Activities/Fragments), promoting clean architecture, testability, and separation of concerns.

---

## 4. Room Database: Architecture and Integration

**Room** is a persistence library provided by Android Jetpack. It acts as an abstraction layer over SQLite, making database access smooth and robust while harnessing the full power of SQLite.

### How Room Works:
1. **SQLite Wrapper:** Under the hood, Room creates and manages the raw SQLite database files.
2. **Compile-Time Verification:** Unlike raw SQLite where syntax errors crash the app at runtime, Room checks all your SQL queries at compile time.
3. **Reactive Queries with Coroutines & Flow:** Room integrates seamlessly with Kotlin Coroutines (`suspend` functions for background thread execution) and Kotlin `Flow` (for observing real-time database changes).

### Integration with the Android Data Layer:
Room sits at the bottom of the data layer, feeding data upward through a **Repository Pattern** into **ViewModels**, which then expose StateFlow or LiveData to the **UI Layer (Activities/Fragments/Compose)**.

```
[ UI Layer (Fragments / Compose) ]
               ▲
               │ Observes State
[ ViewModel ]  │
               ▲
               │ Fetches Data
[ Repository ] ◄── Manages data sources (Local Room DB vs Remote API)
               ▲
               │ Queries / Inserts
[ Room Database (DAO & Entities) ]
```

### Summary Talking Points for Tomorrow's Discussion:
- **Why Room over SQLite?** Room eliminates boilerplate code, provides compile-time SQL validation, and natively supports modern reactive programming (`Flow` / Coroutines).
- **Separation of Concerns:** Entities define structure, DAOs define operations, and the Room Database class acts as the access point.
- **Thread Safety:** Database operations never run on the main UI thread; Room enforces background execution using Coroutines.
