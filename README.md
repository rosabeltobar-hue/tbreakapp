# TBreak App 🌿

An Android application designed to help users take healthy breaks from cannabis use, featuring monetization through in-app purchases and a donation-based support system.

## Features

- 🎯 **Break Tracking**: Track your cannabis break sessions
- 💎 **Premium Features**: Remove ads and unlock advanced tracking
- 💚 **Donation Support**: Multiple donation tiers to support development
- 🔒 **Secure Billing**: Integrated with Google Play Billing Library
- 🎨 **Modern UI**: Material Design 3 with intuitive interface

## Monetization Features

### In-App Purchases
- **Premium (No Ads)** - $2.99
  - Remove all advertisements
  - Unlock advanced break tracking features
  - One-time purchase

### Donations
Support the app development with flexible donation options:
- **Small Coffee** ☕ - $0.99
- **Meal Support** 🍔 - $2.99
- **Generous Supporter** 💚 - $4.99

All donations are consumable, allowing users to donate multiple times.

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK (API level 24+)
- Google Play Developer Account (for publishing)

### Building the App

1. Clone the repository:
```bash
git clone https://github.com/rosabeltobar-hue/tbreakapp.git
cd tbreakapp
```

2. Open the project in Android Studio

3. Sync Gradle dependencies

4. Build and run:
```bash
./gradlew assembleDebug
```

### Setting Up Monetization

Before publishing to Google Play Store, you need to:

1. **Create In-App Products** in Google Play Console:
   - `premium_no_ads` (Non-consumable, $2.99)
   - `donation_small` (Consumable, $0.99)
   - `donation_medium` (Consumable, $2.99)
   - `donation_large` (Consumable, $4.99)

2. **Configure App Signing** in Play Console

3. **Set Up License Testing** for development testing

For detailed instructions, see [MONETIZATION_GUIDE.md](MONETIZATION_GUIDE.md)

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Material Design 3
- **Billing**: Google Play Billing Library 6.1.0
- **Architecture**: MVVM pattern
- **Async**: Kotlin Coroutines
- **Build System**: Gradle

## Project Structure

```
tbreakapp/
├── app/
│   ├── src/main/
│   │   ├── java/com/tbreakapp/
│   │   │   ├── MainActivity.kt          # Main app activity
│   │   │   └── BillingHelper.kt         # Billing management
│   │   ├── res/
│   │   │   ├── layout/                  # UI layouts
│   │   │   ├── values/                  # Strings, colors, themes
│   │   │   ├── menu/                    # App menu
│   │   │   └── mipmap/                  # App icons
│   │   └── AndroidManifest.xml
│   └── build.gradle                     # App-level dependencies
├── build.gradle                         # Project-level build config
├── settings.gradle                      # Project settings
├── MONETIZATION_GUIDE.md               # Detailed setup guide
└── README.md                           # This file
```

## Testing

### Testing Purchases
1. Add test accounts in Play Console under License Testing
2. Install the app from Internal Testing track
3. Make test purchases (won't be charged)
4. Verify purchase flow and UI updates

### Local Testing
```bash
./gradlew test
```

## Publishing to Google Play Store

1. **Prepare Release Build**
   ```bash
   ./gradlew bundleRelease
   ```

2. **Sign the App** with your release keystore

3. **Upload to Play Console**
   - Create a new release
   - Upload the signed App Bundle
   - Complete store listing
   - Submit for review

See [MONETIZATION_GUIDE.md](MONETIZATION_GUIDE.md) for complete publishing instructions.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is open source and available under the MIT License.

## Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Contact: [Your contact information]

## Acknowledgments

- Built with ❤️ to help people take healthy breaks
- Thanks to all contributors and supporters
- Powered by Google Play Billing

---

**Note**: This app requires a Google Play Developer account to publish and enable in-app purchases. See the monetization guide for complete setup instructions.

