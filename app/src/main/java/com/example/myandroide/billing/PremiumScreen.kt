package com.example.myandroide.billing

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PremiumScreen(billingViewModel: BillingViewModel) {
    val products by billingViewModel.products.collectAsState()
    val purchases by billingViewModel.purchases.collectAsState()
    val context = LocalContext.current

    val isPremium = purchases.any { it.products.contains("premium_upgrade") && it.isAcknowledged }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPremium) {
            Text("You are a premium user!")
        } else {
            Text("Upgrade to premium to unlock more features!")
            products.forEach { product ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(product.name)
                    Button(onClick = { billingViewModel.launchPurchaseFlow(context as Activity, product) }) {
                        Text("Buy")
                    }
                }
            }
        }
    }
}