# TBreak App - Monetization and Donation Setup Guide

## Overview
TBreak App is an Android application designed to help users take healthy breaks from cannabis use. The app includes monetization features through in-app purchases and a donation-based system.

## Features Implemented

### 1. **In-App Purchases (IAP)**
- **Premium Purchase**: Remove ads feature ($2.99)
- **Donation Options**: Three donation tiers for users who want to support the app

### 2. **Google Play Billing Integration**
The app uses the Google Play Billing Library v6.1.0 for all monetization features.

## Product IDs Configuration

The following product IDs need to be configured in Google Play Console:

### In-App Products (One-time purchases)

1. **Premium (No Ads)**
   - Product ID: `premium_no_ads`
   - Type: Non-consumable
   - Price: $2.99 (or local equivalent)
   - Description: "Remove all ads and unlock premium features"

2. **Small Donation**
   - Product ID: `donation_small`
   - Type: Consumable
   - Price: $0.99 (or local equivalent)
   - Description: "Small donation - buy us a coffee ☕"

3. **Medium Donation**
   - Product ID: `donation_medium`
   - Type: Consumable
   - Price: $2.99 (or local equivalent)
   - Description: "Medium donation - meal support 🍔"

4. **Large Donation**
   - Product ID: `donation_large`
   - Type: Consumable
   - Price: $4.99 (or local equivalent)
   - Description: "Large donation - generous supporter 💚"

## Google Play Console Setup

### Step 1: Create a Google Play Developer Account
1. Go to [Google Play Console](https://play.google.com/console)
2. Sign in with your Google account
3. Pay the one-time registration fee ($25)
4. Complete your account details

### Step 2: Create Your App
1. Click "Create app" in the Play Console
2. Fill in the app details:
   - App name: TBreak App
   - Default language: English (United States)
   - App or game: App
   - Free or paid: Free
3. Accept the declarations and create the app

### Step 3: Configure In-App Products
1. In your app's dashboard, go to "Monetize" → "Products" → "In-app products"
2. Click "Create product" for each product ID listed above
3. For each product:
   - Enter the Product ID exactly as shown above
   - Set the Name and Description
   - Set the Price
   - Set Status to "Active"
   - For donations, mark as "Consumable"
   - For premium, mark as "Non-consumable"

### Step 4: App Signing
1. Go to "Release" → "Setup" → "App signing"
2. Opt in to Play App Signing (recommended)
3. Upload your app bundle or APK

### Step 5: Create a Release
1. Go to "Release" → "Production"
2. Click "Create new release"
3. Upload your signed APK or App Bundle
4. Complete the release notes and submit for review

## Building the App

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK with API level 34

### Build Instructions

1. **Clone the repository**
```bash
git clone https://github.com/rosabeltobar-hue/tbreakapp.git
cd tbreakapp
```

2. **Open in Android Studio**
- Open Android Studio
- Select "Open an existing project"
- Navigate to the project directory and open it

3. **Sync Gradle**
- Android Studio will automatically sync Gradle
- Wait for the sync to complete

4. **Build the APK**
```bash
./gradlew assembleRelease
```
or use Android Studio: Build → Build Bundle(s) / APK(s) → Build APK(s)

5. **Build App Bundle (for Play Store)**
```bash
./gradlew bundleRelease
```
or use Android Studio: Build → Build Bundle(s) / APK(s) → Build Bundle(s)

### Signing the App
For release builds, you need to sign your app:

1. **Generate a signing key**
```bash
keytool -genkey -v -keystore tbreakapp-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias tbreakapp
```

2. **Configure signing in `app/build.gradle`**
```gradle
android {
    signingConfigs {
        release {
            storeFile file("path/to/tbreakapp-release-key.jks")
            storePassword "your-keystore-password"
            keyAlias "tbreakapp"
            keyPassword "your-key-password"
        }
    }
    buildTypes {
        release {
            signingConfig signingConfigs.release
            // ... other config
        }
    }
}
```

**Important**: Never commit your keystore file or passwords to version control!

## Testing In-App Purchases

### Test with Google Play Console
1. Add test accounts in Play Console:
   - Go to "Setup" → "License testing"
   - Add Gmail accounts for testing
2. Install the app from Play Console (Internal testing track)
3. Test purchases with the test accounts
4. Test purchases will not be charged

### Test in Development
1. Use debug builds with license test accounts
2. The billing flow will show "Test purchase" for test accounts
3. Verify purchase success/failure callbacks work correctly

## Code Architecture

### BillingHelper.kt
Manages all Google Play Billing operations:
- Connection to billing service
- Product detail queries
- Purchase flows
- Purchase acknowledgment
- Consumption (for donations)

### MainActivity.kt
Main app UI that:
- Displays donation options
- Shows premium features
- Handles purchase button clicks
- Updates UI based on purchase state

## User Experience Flow

1. **Free User**
   - Can use basic app features
   - Sees ads (when ad integration is added)
   - Can donate or purchase premium

2. **Premium User**
   - No ads
   - Advanced tracking features unlocked
   - Premium badge/status

3. **Donor**
   - Can donate multiple times (consumable)
   - Receives thank you message
   - Supports app development

## Privacy and Compliance

### Required Play Store Listings
1. **Privacy Policy**: Must disclose data collection and usage
2. **Terms of Service**: Define usage terms and refund policy
3. **Refund Policy**: Must comply with Google Play policies

### Data Handling
- The app uses Google Play Billing (handled by Google)
- No credit card data is stored in the app
- Purchase history is managed by Google Play

## Monetization Best Practices

1. **Clear Value Proposition**: Premium features should provide clear value
2. **Non-intrusive Prompts**: Don't spam users with donation requests
3. **Multiple Options**: Offer various donation amounts
4. **Gratitude**: Always thank users for their support
5. **Transparency**: Be clear about what donations support

## Troubleshooting

### Common Issues

**Billing not available**
- Ensure app is uploaded to Play Console
- Verify product IDs are created and active
- Check that app is signed with release key
- Test with licensed test account

**Purchase not updating**
- Verify acknowledgement/consumption is working
- Check purchase verification logic
- Test with fresh install

**Product not found**
- Ensure product IDs match exactly
- Verify products are active in Play Console
- Check spelling and capitalization

## Support and Resources

- [Google Play Billing Documentation](https://developer.android.com/google/play/billing)
- [Play Console Help](https://support.google.com/googleplay/android-developer)
- [Android Developer Documentation](https://developer.android.com)

## License
This project is open source and available under the MIT License.

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.
