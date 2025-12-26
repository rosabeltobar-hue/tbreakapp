package com.tbreakapp

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Helper class to manage Google Play Billing for monetization and donations
 */
class BillingHelper(private val context: Context) {
    
    private var billingClient: BillingClient? = null
    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        }
    }
    
    // Product IDs for the app
    companion object {
        const val PREMIUM_PRODUCT_ID = "premium_no_ads"
        const val DONATION_SMALL_ID = "donation_small"
        const val DONATION_MEDIUM_ID = "donation_medium"
        const val DONATION_LARGE_ID = "donation_large"
        
        private val DONATION_PRODUCT_IDS = listOf(
            DONATION_SMALL_ID,
            DONATION_MEDIUM_ID,
            DONATION_LARGE_ID
        )
    }
    
    /**
     * Start connection to Google Play Billing
     */
    fun startConnection(onComplete: (Boolean) -> Unit) {
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
        
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
            
            override fun onBillingServiceDisconnected() {
                // Try to reconnect
                startConnection(onComplete)
            }
        })
    }
    
    /**
     * Check if user has purchased premium
     * Note: In Billing Library 6.x, queryPurchasesAsync is a suspending function
     */
    suspend fun isPremiumUser(): Boolean = withContext(Dispatchers.Main) {
        val client = billingClient ?: return@withContext false
        
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()
        
        // queryPurchasesAsync is a suspending function in billing library 6.1.0
        val purchasesResult = client.queryPurchasesAsync(params)
        
        if (purchasesResult.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            return@withContext purchasesResult.purchasesList.any { purchase ->
                purchase.products.contains(PREMIUM_PRODUCT_ID) && 
                purchase.purchaseState == Purchase.PurchaseState.PURCHASED
            }
        }
        
        return@withContext false
    }
    
    /**
     * Purchase premium (remove ads)
     */
    suspend fun purchasePremium(activity: Activity, callback: (Boolean, String?) -> Unit) {
        launchPurchaseFlow(activity, PREMIUM_PRODUCT_ID, BillingClient.ProductType.INAPP, callback)
    }
    
    /**
     * Make a donation
     */
    suspend fun makeDonation(activity: Activity, donationProductId: String, callback: (Boolean, String?) -> Unit) {
        if (!DONATION_PRODUCT_IDS.contains(donationProductId)) {
            callback(false, "Invalid donation product ID")
            return
        }
        launchPurchaseFlow(activity, donationProductId, BillingClient.ProductType.INAPP, callback)
    }
    
    /**
     * Launch purchase flow for a product
     */
    private suspend fun launchPurchaseFlow(
        activity: Activity,
        productId: String,
        productType: String,
        callback: (Boolean, String?) -> Unit
    ) = withContext(Dispatchers.Main) {
        val client = billingClient
        
        if (client == null || !client.isReady) {
            callback(false, "Billing service not ready")
            return@withContext
        }
        
        // Query product details
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(productType)
                .build()
        )
        
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()
        
        // Query product details - this is a suspending function
        // It's safe to call on Main dispatcher as it's non-blocking
        val productDetailsResult = client.queryProductDetails(params)
        
        if (productDetailsResult.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            val productDetails = productDetailsResult.productDetailsList?.firstOrNull()
            
            if (productDetails != null) {
                val productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .build()
                )
                
                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()
                
                val billingResult = client.launchBillingFlow(activity, billingFlowParams)
                
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Billing flow launched successfully. Purchase result will be delivered
                    // to PurchasesUpdatedListener. For simplicity, we indicate success here
                    // to mean "user can now complete the purchase in the dialog"
                    callback(true, null)
                } else {
                    callback(false, "Failed to launch billing flow: ${billingResult.debugMessage}")
                }
            } else {
                callback(false, "Product not found")
            }
        } else {
            callback(false, "Failed to query product details: ${productDetailsResult.billingResult.debugMessage}")
        }
    }
    
    /**
     * Handle purchase after successful transaction
     */
    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                acknowledgePurchase(purchase)
            }
        }
    }
    
    /**
     * Acknowledge purchase (required for non-consumable products)
     */
    private fun acknowledgePurchase(purchase: Purchase) {
        val client = billingClient ?: return
        
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        
        client.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                // Purchase acknowledged successfully
            }
        }
        
        // For donations (consumable), we should consume them so users can donate again
        if (purchase.products.any { it in DONATION_PRODUCT_IDS }) {
            consumePurchase(purchase)
        }
    }
    
    /**
     * Consume purchase (for donations to allow repeated purchases)
     */
    private fun consumePurchase(purchase: Purchase) {
        val client = billingClient ?: return
        
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        
        client.consumeAsync(consumeParams) { billingResult, _ ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                // Purchase consumed, user can donate again
            }
        }
    }
    
    /**
     * End billing connection
     */
    fun endConnection() {
        billingClient?.endConnection()
    }
}
