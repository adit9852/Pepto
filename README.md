![](/readme-media/graphic.svg)

# 🍲 Pepto (Kotlin Multiplatform Mobile)

Pepto is a modern, aesthetic _food‑delivery_ sample app built to demonstrate the use of
_**Kotlin Multiplatform Mobile**_ for developing _Android and iOS_ applications with a single,
shared **Compose Multiplatform** UI 🚀.

| Platforms | ![](https://img.shields.io/badge/Android-black.svg?style=for-the-badge&logo=android) ![](https://img.shields.io/badge/iOS-black.svg?style=for-the-badge&logo=apple)  |
|-----------|---|
| Status    | [![Build](https://github.com/adit9852/Foodium/actions/workflows/build.yml/badge.svg)](https://github.com/adit9852/Foodium/actions/workflows/build.yml)  |


## About

Pepto is a Zomato‑style food‑delivery front end with a clean, modern interface — a location
header, search bar, food categories, a "Popular near you" carousel and restaurant cards with
ratings, delivery times and prices. It loads data from an API and caches it in a local SQLite
database, so content stays available offline and remote/local data are always synchronized.

**Features:**

- [x] Modern, aesthetic food‑delivery UI 🎨
- [x] Single shared Compose UI across Android & iOS 📱
- [x] Offline capability (SQLite cache) 📵
- [x] Dark mode 🌓
- [x] Pull‑to‑refresh & shimmer loading ✨

The network API is a dummy (fixed) response which is _statically hosted
[here](https://adit9852.github.io/DummyFoodiumApi/api/posts/)_.

### 📱 Preview

Here's how Pepto looks on both platforms:

#### 📸 Screenshots

<!--
  Add your screenshots to the `readme-media/` folder and keep these file names
  (or update the paths below). Recommended: a portrait screenshot ~1080px wide.
-->
<table>
  <tr>
    <th>Android</th>
    <th>iOS</th>
  </tr>
  <tr>
    <td><img src="readme-media/pepto-android.png" width="280" alt="Pepto on Android"/></td>
    <td><img src="readme-media/pepto-ios.png" width="280" alt="Pepto on iOS"/></td>
  </tr>
</table>

#### ▶️ Android

<!--
  Paste your Android demo VIDEO link on the line below.
  See "Adding your own demo media" at the bottom for how to get a GitHub video URL.
-->
_Add your Android demo video here._

#### ▶️ iOS

<!-- Paste your iOS demo VIDEO link on the line below. -->
_Add your iOS demo video here._

---

## Built with

- [Kotlin](https://kotlinlang.org): Programming language
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html): For building multi-platform applications in a single codebase.
- [Jetpack/JetBrains Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/): For a shared UI between Android and iOS.
- Kotlinx
  - [Coroutines](https://github.com/Kotlin/kotlinx.coroutines): For multithreading
  - [Serialization](https://github.com/Kotlin/kotlinx.serialization): For JSON serialization/deserialization
- [Ktor Client](https://github.com/ktorio/ktor): Performing HTTP requests; image loading utility for the iOS module.
- [SQLDelight](https://github.com/cashapp/sqldelight): For persisting data in the local database
- [Coil](https://github.com/coil-kt/coil): Image loading for Android
- [Mutekt](https://github.com/patilshreyas/mutekt): For UI state management

## Setting up project 👨🏻‍💻

- Refer to the ***"Setting up environment"*** section of [this repository](https://github.com/JetBrains/compose-multiplatform-ios-android-template/blob/main/README.md)
for the setup guidelines.
- After validating requirements as per the above guide, clone this repository.
- Open this project in Android Studio (Electric Eel or newer).
- Build the project 🔨 and confirm everything works.
- Run the app
  - Select **"androidApp"** as the run configuration to run the Android app.
  - Select **"iosApp"** as the run configuration to run the iOS app _(Xcode can also be used)_.

## Project structure

This Compose Multiplatform project includes three modules:

### [`shared`](/shared)
A Kotlin module containing the logic common to both Android and iOS — the code you share between platforms.
This is also where the Compose Multiplatform UI lives. In `shared/src/commonMain/kotlin/App.kt` you'll find the shared root `@Composable` function for the app.
It uses Gradle as the build system; add dependencies and change settings in `shared/build.gradle.kts`. The shared module builds into an Android library and an iOS framework.

### [`androidApp`](/androidApp)
A Kotlin module that builds into an Android application. It depends on and uses the shared module as a regular Android library.

### [`iosApp`](/iosApp)
An Xcode project that builds into an iOS application. It depends on and uses the shared module as a CocoaPods dependency.

---

## Adding your own demo media 🎥

This README is designed to show a hero banner plus an Android and iOS demo — here's how to fill it in:

**Hero banner (the "front photo"):**
- The banner at the top is [`readme-media/graphic.svg`](/readme-media/graphic.svg). Edit it directly, or replace the line `![](/readme-media/graphic.svg)` with your own image (e.g. `![](/readme-media/graphic.png)`).

**Screenshots:**
1. **Android:** run the app, then capture with `adb exec-out screencap -p > readme-media/pepto-android.png`.
2. **iOS:** run on a Simulator, then `Cmd+S` (or `xcrun simctl io booted screenshot readme-media/pepto-ios.png`).

**Demo videos (the part that actually *plays* in the README):**
- Record the screen — Android: `adb shell screenrecord /sdcard/demo.mp4` (then `adb pull`); iOS Simulator: `xcrun simctl io booted recordVideo demo.mov`.
- GitHub does **not** play repo-hosted `.mp4` files inline. Instead, open any issue / pull request / the README editor on **github.com**, **drag‑and‑drop your video** into the text box, and GitHub uploads it and inserts a `https://user-images.githubusercontent.com/…​.mp4` link.
- Copy that link and paste it on its own line under the **▶️ Android** / **▶️ iOS** headings (replacing the `_Add your … video here._` placeholder). GitHub renders it as an inline video player. 🎬

## Contribute

If you want to contribute, you're always welcome!
See [Contributing Guidelines](CONTRIBUTING.md).

## Discuss 💬

Have any questions, doubts or want to present your opinions/views? You're always welcome.
You can [start discussions](https://github.com/adit9852/Foodium-KMM/discussions).

## Acknowledgements

- [JetBrains/compose-multiplatform-ios-android-template](https://github.com/JetBrains/compose-multiplatform-ios-android-template#readme):
  Starter template
- [google/accompanist](https://github.com/google/accompanist): For the placeholder (shimmer animation) APIs

## License

```
Copyright 2025 Aditya Kumar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
