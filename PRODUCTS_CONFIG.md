# In-App Products Configuration

This document lists all the in-app products that need to be configured in Google Play Console.

## Product Details

### 1. Premium - No Ads

**Configuration:**
```
Product ID: premium_no_ads
Product Type: Non-consumable (one-time purchase)
Status: Active
```

**Pricing:**
- United States (USD): $2.99
- Automatically calculate for other countries: Yes

**Store Listing:**
```
Name: Premium - No Ads
Description: Remove all advertisements and unlock premium features including advanced break tracking and detailed analytics. Support the app and enjoy an ad-free experience!
```

**Details:**
- **Category:** App Functionality
- **Consumption Type:** Non-consumable
- **Declaration:** Includes digital content

---

### 2. Small Donation

**Configuration:**
```
Product ID: donation_small
Product Type: Consumable (can be purchased multiple times)
Status: Active
```

**Pricing:**
- United States (USD): $0.99
- Automatically calculate for other countries: Yes

**Store Listing:**
```
Name: Small Donation - Coffee
Description: Buy us a coffee! ☕ Your small donation helps keep TBreak App free and supports ongoing development. Every contribution matters!
```

**Details:**
- **Category:** Donation
- **Consumption Type:** Consumable
- **Declaration:** Includes digital content

---

### 3. Medium Donation

**Configuration:**
```
Product ID: donation_medium
Product Type: Consumable (can be purchased multiple times)
Status: Active
```

**Pricing:**
- United States (USD): $2.99
- Automatically calculate for other countries: Yes

**Store Listing:**
```
Name: Medium Donation - Meal Support
Description: Support us with a meal! 🍔 Your generous donation helps us maintain and improve TBreak App for everyone. Thank you for your support!
```

**Details:**
- **Category:** Donation
- **Consumption Type:** Consumable
- **Declaration:** Includes digital content

---

### 4. Large Donation

**Configuration:**
```
Product ID: donation_large
Product Type: Consumable (can be purchased multiple times)
Status: Active
```

**Pricing:**
- United States (USD): $4.99
- Automatically calculate for other countries: Yes

**Store Listing:**
```
Name: Large Donation - Generous Supporter
Description: Become a generous supporter! 💚 Your amazing donation makes a huge difference in keeping TBreak App free and feature-rich. You're awesome!
```

**Details:**
- **Category:** Donation
- **Consumption Type:** Consumable
- **Declaration:** Includes digital content

---

## Setup Instructions in Google Play Console

### Step-by-Step Process:

1. **Navigate to Products**
   - Open your app in Play Console
   - Go to: Monetize → Products → In-app products

2. **Create Each Product**
   - Click "Create product"
   - Enter the Product ID exactly as shown above
   - Fill in the title and description
   - Set the pricing
   - Select whether it's consumable or non-consumable
   - Save and activate

3. **Important Settings**

   **For Premium (premium_no_ads):**
   - ✅ Set as Non-consumable
   - ✅ One-time purchase
   - ✅ Activates immediately upon purchase
   - ✅ Persists across devices (linked to Google account)

   **For Donations (all three):**
   - ✅ Set as Consumable
   - ✅ Can be purchased multiple times
   - ✅ Consumed immediately after purchase
   - ✅ Allows repeated donations

4. **Verify Configuration**
   - All product IDs match the code exactly
   - All products are set to "Active" status
   - Pricing is correct in primary market (USD)
   - Descriptions are clear and compelling
   - Consumption type is correct

5. **Test Configuration**
   - Add test accounts under License Testing
   - Install app from internal testing track
   - Verify all products appear correctly
   - Test purchase flow for each product
   - Confirm consumable products can be purchased multiple times

## Price Points Strategy

### Current Pricing Rationale:

**Premium ($2.99):**
- Competitive price point for ad removal
- Perceived value for features
- Low barrier to conversion
- Covers development costs

**Small Donation ($0.99):**
- Impulse-friendly price
- "Buy us a coffee" mentality
- Low commitment level
- Easy first-time donation

**Medium Donation ($2.99):**
- Same as premium price
- "Meal support" framing
- Good value perception
- Popular donation tier

**Large Donation ($4.99):**
- Premium supporter tier
- Shows serious commitment
- Higher margin contribution
- Reserved for enthusiastic supporters

### Price Testing Recommendations:

Consider A/B testing:
- Premium at $1.99 vs $2.99 vs $3.99
- Adding a $9.99 "Super Supporter" donation tier
- Limited-time promotional pricing
- Bundle offerings (future feature)

## Product Lifecycle Management

### Monitoring:
- Track conversion rates for each product
- Monitor refund requests
- Analyze donation patterns
- Review customer feedback

### Optimization:
- Adjust descriptions based on feedback
- Test different price points
- Add seasonal promotional text
- Update based on feature additions

### Future Products (Ideas):
```
Product ID: premium_lifetime
Description: Lifetime premium access with all future features
Price: $9.99

Product ID: donation_super
Description: Super supporter donation
Price: $9.99

Product ID: feature_advanced_analytics
Description: Unlock advanced analytics dashboard
Price: $1.99
```

## Code Reference

Product IDs are defined in `BillingHelper.kt`:

```kotlin
companion object {
    const val PREMIUM_PRODUCT_ID = "premium_no_ads"
    const val DONATION_SMALL_ID = "donation_small"
    const val DONATION_MEDIUM_ID = "donation_medium"
    const val DONATION_LARGE_ID = "donation_large"
}
```

**Important:** If you change product IDs in Play Console, you MUST update these constants in the code.

## Troubleshooting

### Product Not Found Error
- Verify product ID spelling matches exactly
- Confirm product is "Active" in Play Console
- Check app is signed with release key
- Ensure testing with licensed account

### Purchase Not Completing
- Verify acknowledgement/consumption code
- Check billing client connection
- Review purchase flow logs
- Test with different Google accounts

### Donations Not Consumable
- Confirm product type is "Consumable" in Play Console
- Verify consumption code in BillingHelper
- Check that consumeAsync is called
- Test purchase again after consumption

## Compliance

### Google Play Policies:
- Products must provide real value
- Descriptions must be accurate
- No misleading claims
- Proper handling of subscriptions (if added)
- Respect user refund requests

### Tax Considerations:
- Google handles tax collection
- Pricing includes applicable taxes
- Revenue shares after Google's 15-30% fee
- Consult tax professional for business implications

## Revenue Projections

**Conservative Estimates (1000 active users):**
- 2% premium conversion: 20 users × $2.99 = $59.80
- 1% donation rate: 10 users × $2.50 avg = $25.00
- Monthly revenue: ~$85
- After Google fee (30%): ~$60

**Optimistic Estimates (10,000 active users):**
- 5% premium conversion: 500 users × $2.99 = $1,495
- 3% donation rate: 300 users × $2.50 avg = $750
- Monthly revenue: ~$2,245
- After Google fee (15-30%): ~$1,600-1,900

*Note: Actual results will vary significantly based on user engagement, marketing, and app quality.*
