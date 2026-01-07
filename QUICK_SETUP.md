# Quick Setup Guide for Google Play Store

## Before You Publish

### 1. Complete the App
- [ ] Test all features thoroughly
- [ ] Add actual app icon graphics (replace placeholder icons)
- [ ] Implement actual break tracking functionality
- [ ] Add ads integration (if using ads)
- [ ] Test on multiple devices and Android versions

### 2. Google Play Console Account
- [ ] Create Google Play Developer account ($25 one-time fee)
- [ ] Complete account verification
- [ ] Set up payment profile

### 3. Create In-App Products

Log into Play Console and create these products:

#### Premium Product (Non-consumable)
```
Product ID: premium_no_ads
Name: Premium - No Ads
Description: Remove all ads and unlock premium features
Price: $2.99 USD
```

#### Donation Products (Consumable)
```
Product ID: donation_small
Name: Small Donation
Description: Buy us a coffee ☕
Price: $0.99 USD

Product ID: donation_medium
Name: Medium Donation
Description: Meal support 🍔
Price: $2.99 USD

Product ID: donation_large
Name: Large Donation
Description: Generous supporter 💚
Price: $4.99 USD
```

### 4. App Store Listing

Prepare these materials:

**Required**:
- App title: TBreak App
- Short description (80 chars max)
- Full description (4000 chars max)
- App icon (512x512 PNG)
- Feature graphic (1024x500 PNG)
- At least 2 screenshots per supported device type
- Privacy policy URL

**Categories**:
- Primary: Health & Fitness
- Tags: health, wellness, tracking, break, support

### 5. Build Release

```bash
# Generate signing key
keytool -genkey -v -keystore tbreakapp-release-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 -alias tbreakapp

# Build signed release
./gradlew bundleRelease

# Output will be at:
# app/build/outputs/bundle/release/app-release.aab
```

### 6. Testing

**Internal Testing**:
1. Create internal testing track in Play Console
2. Upload your app bundle
3. Add test users
4. Test all purchase flows
5. Verify billing works correctly

**Closed Alpha/Beta**:
1. Expand to alpha/beta testing
2. Get user feedback
3. Fix any issues

### 7. Production Release

1. Complete all Play Console requirements:
   - Content rating questionnaire
   - Target audience
   - News apps declaration (if applicable)
   - COVID-19 contact tracing/status apps (if applicable)
   - Data safety form
   - Privacy policy

2. Create production release:
   - Upload signed app bundle
   - Add release notes
   - Set rollout percentage (start with 20% recommended)
   - Submit for review

### 8. Post-Launch

- Monitor crash reports
- Respond to user reviews
- Update products based on feedback
- Regular updates and improvements

## Important Notes

⚠️ **Before going live**:
- Test all purchase flows thoroughly
- Ensure products are activated in Play Console
- Verify privacy policy is accessible
- Check all store listing materials
- Test on various devices and OS versions

📱 **App Requirements**:
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)
- Compiled SDK: API 34

🔐 **Security**:
- Never commit signing keys to repository
- Keep keystore password secure
- Use Play App Signing (recommended)
- Store credentials securely

## Estimated Timeline

- **Play Console Setup**: 1-2 hours
- **Product Configuration**: 30 minutes
- **First Review**: 1-7 days
- **Updates**: 1-3 days after initial approval

## Resources

- [Google Play Console](https://play.google.com/console)
- [Monetization Guide](MONETIZATION_GUIDE.md)
- [Play Billing Documentation](https://developer.android.com/google/play/billing)
