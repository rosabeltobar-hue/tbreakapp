package com.tbreakapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.ProductDetails
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch

/**
 * Main Activity for TBreak App
 * Supports cannabis break tracking with monetization through donations
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var billingHelper: BillingHelper
    private var isPremiumUser = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize billing
        billingHelper = BillingHelper(this)
        
        setupUI()
        setupBilling()
    }
    
    private fun setupUI() {
        setSupportActionBar(findViewById(R.id.toolbar))
        
        // Start Break button
        findViewById<MaterialButton>(R.id.btn_start_break).setOnClickListener {
            if (isPremiumUser) {
                startBreakSession()
            } else {
                showPremiumFeatureDialog()
            }
        }
        
        // Donation cards
        findViewById<MaterialCardView>(R.id.card_donate_small).setOnClickListener {
            makeDonation("donation_small")
        }
        
        findViewById<MaterialCardView>(R.id.card_donate_medium).setOnClickListener {
            makeDonation("donation_medium")
        }
        
        findViewById<MaterialCardView>(R.id.card_donate_large).setOnClickListener {
            makeDonation("donation_large")
        }
        
        // Remove Ads button
        findViewById<MaterialButton>(R.id.btn_remove_ads).setOnClickListener {
            purchasePremium()
        }
    }
    
    private fun setupBilling() {
        billingHelper.startConnection { success ->
            if (success) {
                // Check if user already purchased premium
                lifecycleScope.launch {
                    isPremiumUser = billingHelper.isPremiumUser()
                    updateUIForPremium()
                }
            } else {
                Toast.makeText(this, "Billing service unavailable", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeDonation(productId: String) {
        lifecycleScope.launch {
            billingHelper.makeDonation(this@MainActivity, productId) { success, message ->
                if (success) {
                    showThankYouDialog()
                } else {
                    Toast.makeText(this@MainActivity, message ?: "Donation failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun purchasePremium() {
        lifecycleScope.launch {
            billingHelper.purchasePremium(this@MainActivity) { success, message ->
                if (success) {
                    isPremiumUser = true
                    updateUIForPremium()
                    Toast.makeText(this@MainActivity, "Premium activated! Ads removed.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MainActivity, message ?: "Purchase failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun updateUIForPremium() {
        if (isPremiumUser) {
            findViewById<MaterialButton>(R.id.btn_remove_ads)?.text = "Premium Active ✓"
            findViewById<MaterialButton>(R.id.btn_remove_ads)?.isEnabled = false
        }
    }
    
    private fun startBreakSession() {
        Toast.makeText(this, "Starting cannabis break session...", Toast.LENGTH_SHORT).show()
        // Add break session logic here
    }
    
    private fun showPremiumFeatureDialog() {
        AlertDialog.Builder(this)
            .setTitle("Premium Feature")
            .setMessage("Advanced break tracking is a premium feature. Support the app by removing ads!")
            .setPositiveButton("Get Premium") { _, _ -> purchasePremium() }
            .setNegativeButton("Maybe Later", null)
            .show()
    }
    
    private fun showThankYouDialog() {
        AlertDialog.Builder(this)
            .setTitle("Thank You! 💚")
            .setMessage("Your donation helps keep TBreak App free and ad-free for everyone. We appreciate your support!")
            .setPositiveButton("OK", null)
            .show()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_donate -> {
                showDonationDialog()
                true
            }
            R.id.action_about -> {
                showAboutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun showDonationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Support TBreak App")
            .setMessage("Choose a donation amount to support the development of this app:")
            .setPositiveButton("Small ($0.99)") { _, _ -> makeDonation("donation_small") }
            .setNeutralButton("Medium ($2.99)") { _, _ -> makeDonation("donation_medium") }
            .setNegativeButton("Large ($4.99)") { _, _ -> makeDonation("donation_large") }
            .show()
    }
    
    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("About TBreak App")
            .setMessage("TBreak App helps you take healthy breaks from cannabis use.\n\nVersion 1.0\n\nSupport us through donations to keep the app free!")
            .setPositiveButton("OK", null)
            .show()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        billingHelper.endConnection()
    }
}
