package com.example.tenantmanagementsystem

// A data class is suitable here because Tenant mainly stores information.
data class Tenant(
    val name: String,
    val phone: String,
    val rent: String
) {
    // Data Binding calls this method to create the tenant's display text.
    fun summary(): String {
        return "Tenant: $name\nPhone: $phone\nRent: KSh $rent"
    }
}
