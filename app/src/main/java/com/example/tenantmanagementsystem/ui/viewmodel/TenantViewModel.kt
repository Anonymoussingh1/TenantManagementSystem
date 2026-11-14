package com.example.tenantmanagementsystem.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.tenantmanagementsystem.data.local.TenantDatabase
import com.example.tenantmanagementsystem.data.model.Tenant
import com.example.tenantmanagementsystem.data.repository.TenantRepository
import kotlinx.coroutines.launch

class TenantViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TenantRepository
    val tenants: LiveData<List<Tenant>>

    val tenantCountText = MediatorLiveData<String>()
    val totalRentText = MediatorLiveData<String>()
    val allTenantsText = MediatorLiveData<String>()

    init {
        Log.d("TENANT_LIFECYCLE", "      TenantViewModel: created")
        val tenantDao = TenantDatabase.getDatabase(application).tenantDao()
        repository = TenantRepository(tenantDao)
        tenants = repository.tenants

        tenantCountText.addSource(tenants) { list ->
            val count = list.size
            tenantCountText.value = "Tenants\n$count"

            val total = list.sumOf { it.rent.toDoubleOrNull() ?: 0.0 }
            totalRentText.value = "Total rent\nKSh %,.0f".format(total)

            if (list.isEmpty()) {
                allTenantsText.value = "No tenants yet. Add one from the Dashboard."
            } else {
                allTenantsText.value = list.joinToString("\n\n") { it.summary() }
            }
        }
    }

    fun saveTenant(name: String, phone: String, rent: String): Tenant? {
        val cleanName = name.trim()
        val cleanPhone = phone.trim()
        val cleanRent = rent.trim()

        if (cleanName.isEmpty() || cleanPhone.isEmpty() || cleanRent.isEmpty()) {
            return null
        }

        val tenant = Tenant(name = cleanName, phone = cleanPhone, rent = cleanRent)
        viewModelScope.launch {
            repository.addTenant(tenant)
        }
        return tenant
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TENANT_LIFECYCLE", "      TenantViewModel: onCleared")
    }
}
