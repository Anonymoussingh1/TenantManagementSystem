package com.example.tenantmanagementsystem.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tenants")
data class Tenant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val phone: String,
    val rent: String
) : Serializable {
    fun summary(): String {
        return "Tenant: $name\nPhone: $phone\nRent: KSh $rent"
    }
}
