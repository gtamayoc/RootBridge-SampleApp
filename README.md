# 🛡️ RootBridge Sample App

> A lightweight, purposely vulnerable Android application designed as a controlled target for real-time memory scanning and manipulation testing.

## 🎯 Overview

**RootBridge Sample App** is a specialized Android tool built exclusively for educational and research purposes. It serves as a reliable, unobfuscated "dummy target" to test memory scanners, game trainers, and reverse engineering frameworks like RootBridge. 

By maintaining simple, unprotected numeric states in memory, it provides a perfect environment for developing, debugging, and demonstrating memory analysis utilities without the noise of production-level security measures.

## ✨ Key Features

- **Unobfuscated Memory State**: Values are stored as plain 4-byte integers (`Int32`), making them easy to locate.
- **Real-Time UI Synchronization**: Jetpack Compose UI reacts instantly to memory manipulations.
- **Shadow Variables**: Includes a hidden `shadowCoins` variable synchronized with the main state to test parallel address scanning and pointer mapping.
- **Zero Persistence**: No SharedPreferences, DataStore, or Room databases. Everything lives purely in RAM.
- **Modern Android Stack**: Built entirely with Kotlin, Jetpack Compose, and Material Design 3.

## ⚙️ How It Works

The app operates on a simplified MVVM architecture. The core state is managed by a `MainViewModel` that holds a `StateFlow<Int>` for the `coins` value. 

When a user (or a memory tool) modifies the `coins` value in the device's RAM, any subsequent action that triggers a state read or update will immediately reflect the injected value on the UI, proving successful memory manipulation.

## 📱 Usage Instructions

1. **Install the Application**: Deploy the APK to a physical device or emulator (Android 7.0 / API 24 or higher required).
2. **Launch**: Open the app to view the initial **Coins** value (defaults to `500`).
3. **Interact**: Tap the `+1 Coins`, `+10 Coins`, or `Reset` buttons to familiarize yourself with the expected behavior.

## 🔍 Memory Scanning Workflow

This is the standard workflow to test your memory scanning tool against the app:

### Phase 1: Initial Scan
1. Launch **RootBridge Sample App**. Ensure the initial value is `500`.
2. Attach your memory scanner (e.g., RootBridge) to the app's process.
3. Perform a **First Scan** searching for the exact value `500` (Data Type: `Int32` / 4 bytes).

### Phase 2: Refine Search
4. Return to the app and tap `+10 Coins`. The new value is now `510`.
5. In your scanner, perform a **Next Scan** (or filter) looking for `510`.
6. Repeat this process (modify in app → filter in scanner) until you isolate the exact memory address holding the coins value.

### Phase 3: Modify & Verify
7. Once the address is isolated, use your scanner to **Overwrite** the value at that address (e.g., change it to `9999`).
8. Return to the app and tap `+1 Coins` to force a UI refresh.
9. The interface will instantly update to display the new injected value (e.g., `10000`), confirming a successful memory overwrite.

## 🛠️ Technical Details

- **UI Framework**: Jetpack Compose
- **Design System**: Material Design 3
- **Architecture**: Simplified MVVM (Repository and Domain layers are intentionally omitted to keep the memory footprint predictable)
- **State Management**: `StateFlow<Int>`
- **Language**: Kotlin

## 📂 Project Structure

```text
com.example.rootbridge.sample
├── MainActivity.kt        # Compose entry point and UI setup
├── MainViewModel.kt       # StateHolder containing 'coins' and 'shadowCoins'
└── ui/
    └── theme/             # Material Design 3 theme definitions
```

## 🧪 Use Cases

- **Tool Development**: Building and testing custom memory scanners.
- **Ethical Hacking**: Practicing memory isolation and value injection.
- **Educational Demonstrations**: Showing how RAM allocation works in Android processes.
- **QA Automation**: Testing tools that interact directly with app memory instead of the UI.

---

> **⚠️ Disclaimer**
> 
> This application has been created exclusively for **educational and research purposes**. It contains no sensitive information, security mechanisms, or actual monetization features. Use it at your own risk and strictly within controlled, legal testing environments (ethical pentesting and memory analysis tool development).
