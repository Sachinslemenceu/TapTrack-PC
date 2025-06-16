# 🚀 TapTrack PC

**TapTrack PC** is a Kotlin Multiplatform Desktop application built using **JetBrains Compose for Desktop**. It acts as a PC-side companion app for the **TapTrack** ecosystem — designed to establish communication with mobile devices over **UDP**, track incoming device connections, and display interactive data in a responsive, modern UI.

<p align="center">
  <img src="https://github.com/user-attachments/assets/eaa1a16f-ae68-4285-b78e-0b9874a411d6" width="40%" />
  <img src="https://github.com/user-attachments/assets/ff599fe6-e1d5-482b-81e4-ca338f636fc9" width="40%" />
</p>
---

## 🛠️ Key Features

- ✅ **Kotlin Multiplatform Desktop (KMP Desktop-only)**
- ✅ **Jetpack Compose for Desktop UI**
- ✅ **Custom UDP Server Implementation**
- ✅ **Real-time client IP discovery**
- ✅ **Reactive UI with StateFlow and CoroutineScope**
- ✅ **MVVM Architecture + DI-ready structure using Koin**
- ✅ **Multithreaded UDP listener with structured concurrency**
- ✅ **Clean modular codebase with separation of concerns**

---

## ⚙️ Tech Stack

| Layer         | Library/Tool                  |
|---------------|-------------------------------|
| UI            | JetBrains Compose Multiplatform |
| Language      | Kotlin (JVM target)            |
| State Mgt.    | `MutableStateFlow`, `StateFlow`, `rememberSaveable` |
| Networking    | Java/Kotlin `DatagramSocket` (UDP), custom socket abstraction |
| Coroutines    | Kotlinx Coroutines             |
| Logging       | println debug (expandable with Timber or Ktor log) |
| Structure     | MVVM (Model-View-ViewModel)    |
| Build Tool    | Gradle (Kotlin DSL)            |

---

## ⚡ Optimization Highlights

### 🧠 Smart Resource Management
- **Custom `UDPServerService` with safe socket lifecycle**:
  - Ensures socket is opened and closed gracefully.
  - Prevents port lock issues and exceptions on reconnection.

### 🧵 Coroutine Best Practices
- All network operations run on `Dispatchers.IO`.
- Real-time `StateFlow` for emitting client connection data to the UI.
- ViewModel safely collects flow using lifecycle-aware coroutine scope.

### 🧼 Clean Code Structure
- Separation between **services**, **data**, and **UI layers**.
- Optimized `remember` and `rememberSaveable` usage to preserve UI state across recompositions.

---

## 🔌 How It Works

### Network Flow (Simplified)
```text
Mobile Device 📱  -->  [UDP Packet]  -->  TapTrack PC 🖥️
                                         ↳ Extracts IP
                                         ↳ Displays on UI
---


## 🧠 Contribution

Currently an open-source project, but contributors are welcome to fork the repo and commit changes.

---

## 👨‍💻 Author

Built with ❤️ by **Sachin Pradeep Singh**  

---

## 📄 License

MIT License

---
