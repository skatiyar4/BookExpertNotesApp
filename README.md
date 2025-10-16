# BookExpertNotesApp 

### Project Structure

This is a Kotlin Multiplatform project with a structure designed for code sharing between Android and iOS, using Compose Multiplatform for the user interface.

## Core Directories

- **`/composeApp`**: Contains the shared Kotlin code for all platforms.
  - **`commonMain`**: The heart of the application. It holds shared business logic, UI (written in Compose), and data models.
  - **`androidMain`**: Platform-specific code and resources for the Android application.
  - **`iosMain`**: Platform-specific code for the iOS target.
  - **`desktopMain`**: Platform-specific code for the Desktop (JVM) target.

- **`/iosApp`**: The Xcode project for the iOS application. This is the entry point for the iOS app and contains any iOS-specific configurations or SwiftUI code.

## How it Works

- The **Android application** is built directly from the `:composeApp` module.
- The **iOS application** in `/iosApp` integrates the shared code from `/composeApp` as a framework.

This structure allows for maximum code reuse in `commonMain` while still providing the flexibility to write platform-specific code when necessary.

### Architecture (MVVM)

This project utilizes the **Model-View-ViewModel (MVVM)** architecture to create a clean, scalable, and testable codebase.

*   **Model**: Represents the data and business logic of the application. This includes data sources, repositories, and data classes, all of which reside in `commonMain`.
*   **View**: The UI layer of the application. It is built with **Compose Multiplatform** in `commonMain` and is responsible for displaying data and capturing user input. The View observes the ViewModel for state changes and renders the UI accordingly.
*   **ViewModel**: Acts as a bridge between the Model and the View. It holds the UI state, handles user interactions, and calls the necessary business logic from the Model. ViewModels are also located in `commonMain` to be shared across all platforms.

This separation of concerns ensures that the UI (`View`) is decoupled from the business logic (`Model`), making the app easier to maintain and test.

### Libraries and Technologies Used

This project leverages a modern tech stack to enable cross-platform development and a reactive UI.

*   **[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)**: The core framework for sharing code across different platforms like Android, iOS, and Desktop.
*   **[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)**: A declarative UI framework from JetBrains for building shared user interfaces in `commonMain`.
*   **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)**: Used for managing asynchronous operations and background tasks gracefully.
*   **MVVM Architecture**: The architectural pattern used to separate UI, state management, and business logic.
  *   **[Koin](https://insert-koin.io/)**: A lightweight dependency injection framework that works seamlessly in Kotlin Multiplatform.
*   **[Ktor](https://ktor.io/)**: A multiplatform asynchronous framework for creating clients and servers, often used for making network requests.
*   **[Room](https://developer.android.com/training/data-storage/room)**: A persistence library that provides an abstraction layer over SQLite to allow for more robust database access. It is now supported in Kotlin Multiplatform.
*   **[Gradle](https://gradle.org/)**: The build automation tool used to manage dependencies and build the project for all target platforms.


### Handling Web View Interactions

To capture events like button clicks or link navigations within a web view, you need to establish a communication bridge between the web content (HTML/JavaScript) and the native Kotlin code.

#### How It Works

1.  **JavaScript Interface (Android)**: On Android, a JavaScript interface is exposed to the web view. This allows JavaScript code running inside the web view to call Kotlin functions directly.
  *   An `@JavascriptInterface` annotation is used on methods in a class that is then registered with the web view using `addJavascriptInterface()`.

2.  **Message Handlers (iOS)**: On iOS, communication is handled via `WKScriptMessageHandler`.
  *   The web view's `WKUserContentController` is configured to listen for messages from the JavaScript `window.webkit.messageHandlers.<handlerName>.postMessage(...)` function.
  *   A delegate object receives these messages and translates them into calls within the shared Kotlin code.

3.  **Shared Logic (`commonMain`)**: To keep the implementation clean, an `expect` interface is defined in `commonMain` to represent the web view component and its event listeners. The `androidMain` and `iosMain` source sets then provide the `actual` platform-specific implementations.

#### Example Flow for a Button Click:

1.  **Web Content (HTML)**: A button is given a JavaScript `onclick` attribute.
    