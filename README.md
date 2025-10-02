# BlueFrog - A Kotlin Multiplatform Project

This is a Kotlin Multiplatform project targeting Android, iOS, and Desktop, built with a modern tech stack to deliver a seamless cross-platform experience.

## Tech Stack

This project leverages a variety of modern technologies to ensure a robust and maintainable codebase:

- **Kotlin Multiplatform:** For sharing code across different platforms.
- **Jetpack Compose:** For building the UI for Android, iOS, and Desktop.
- **Koin:** For dependency injection.
- **Supabase:** For backend services, including authentication and database.
- **Room:** For local database storage.
- **Ktor:** For networking.
- **Ktlint:** For enforcing Kotlin coding standards.

## Getting Started

To get started with this project, you'll need to have the following installed:

- Android Studio
- Xcode (for iOS development)
- JDK 11 or higher

### Building and Running

- **Android:** Open the project in Android Studio and run the `composeApp` configuration.
- **iOS:** Open the `iosApp` folder in Xcode and run the project on a simulator or a physical device.
- **Desktop:** Run the following command in the root directory of the project:
  ```bash
  ./gradlew :composeApp:run
  ```

## Linting

This project uses `ktlint` to enforce Kotlin coding standards. To run the linter, use the following command:

```bash
./gradlew ktlintCheck
```

To automatically format the code, run:

```bash
./gradlew ktlintFormat
```

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…