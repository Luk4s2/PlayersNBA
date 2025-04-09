# 🏀 NBA Players App

A modern Android app built with **Jetpack Compose** that displays NBA players and their details using the [balldontlie API](https://www.balldontlie.io/).

## ✨ Features

- 🔍 Player list with lazy loading pagination
- 🧍 Player detail screen with rich attributes
- 🏀 Team detail screen
- 📦 Clean architecture with **MVVM**
- 🧪 Unit tested **ViewModels** using Kotlin Coroutines & Turbine
- 🌐 Retrofit with error handling via sealed `ResultWrapper`
- ♻️ Reusable UI components
- 📱 Optimized for performance and UX

## 🧱 Built With

| Tech                  | Description                       |
|-----------------------|------------------------------------|
| 🧩 Jetpack Compose     | Modern UI toolkit                 |
| 🧪 JUnit + Turbine     | ViewModel testing                 |
| 🚀 Kotlin Coroutines   | Async & background operations     |
| 🕸️ Retrofit            | API calls                        |
| 🧭 Navigation Compose | Screens navigation                |
| 💉 Hilt DI            | Dependency injection              |
| 📚 MVVM + Repository   | Scalable architecture             |

## 🧪 Running Unit Tests

```bash
./gradlew test
```

## 🚀 Getting Started

1. Clone the project:
```bash
git clone https://github.com/luk4s2/nba-players-app.git
```

2. Open in Android Studio (Giraffe or newer)

3. Run the app on emulator or physical device.


## 🔑 API

This app uses the free [balldontlie API](https://www.balldontlie.io/) — no authentication required.


## 📄 License

```
MIT License
```
